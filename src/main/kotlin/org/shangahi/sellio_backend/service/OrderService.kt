package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.CartItem
import org.shangahi.sellio_backend.entity.OrderItem
import org.shangahi.sellio_backend.entity.Orders
import org.shangahi.sellio_backend.entity.Product
import org.shangahi.sellio_backend.entity.Store
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.repository.CartItemRepository
import org.shangahi.sellio_backend.repository.CartRepository
import org.shangahi.sellio_backend.repository.OrderItemRepository
import org.shangahi.sellio_backend.repository.OrderRepository
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.service.exception.CartIsEmptyException
import org.shangahi.sellio_backend.service.exception.CartItemQuantityExceedsStockException
import org.shangahi.sellio_backend.service.exception.CartNotFoundException
import org.shangahi.sellio_backend.service.exception.OrderCannotBeCancelledException
import org.shangahi.sellio_backend.service.exception.OrderNotFoundException
import org.shangahi.sellio_backend.service.exception.ProductNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class OrderService(
    private val orderItemRepository: OrderItemRepository,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val productRepository: ProductRepository
) {
    @Transactional
    fun confirmOrder(userId: UUID, note: String?): List<UUID> {
        val cart = cartRepository.findByUserId(userId)
            ?: throw CartNotFoundException()

        val cartItems = cartItemRepository.findByCartId(cart.id!!)
        if (cartItems.isEmpty()) {
            throw CartIsEmptyException()
        }

        val itemsByStore = cartItems.groupBy { it.product.store }
        val createdOrderIds = mutableListOf<UUID>()

        itemsByStore.forEach { (store, storeItems) ->
            val orderId = createOrder(cart.user, store, storeItems, note)
            createdOrderIds.add(orderId)
        }

        cartItemRepository.deleteByCartId(cart.id)

        return createdOrderIds
    }

    private fun createOrder(user: User, store: Store, storeItems: List<CartItem>, note: String?): UUID {
        var orderTotal = BigDecimal.ZERO
        val validatedItems = mutableListOf<Pair<Product, CartItem>>()

        storeItems.forEach { cartItem ->
            val product = productRepository.findByIdWithLock(cartItem.product.id!!)
                ?: throw ProductNotFoundException()

            if (cartItem.quantity > product.stock) {
                throw CartItemQuantityExceedsStockException(product.stock)
            }

            product.stock -= cartItem.quantity
            validatedItems.add(product to cartItem)

            val itemTotal = product.price
                .multiply(BigDecimal.valueOf(cartItem.quantity.toLong()))
            orderTotal = orderTotal.add(itemTotal)
        }

        val order = Orders(
            user = user,
            store = store,
            note = note,
            status = OrderStatus.PROCESSING,
            totalPrice = orderTotal
        )
        orderRepository.save(order)

        val orderItems = validatedItems.map { (product, cartItem) ->
            OrderItem(
                product = product,
                order = order,
                quantity = cartItem.quantity,
                customizationImageUrl = cartItem.customizationImageUrl,
            )
        }
        orderItemRepository.saveAll(orderItems)

        return order.id!!
    }

    @Transactional(readOnly = true)
    fun getOrders(
        userId: UUID,
        status: OrderStatus?,
        pageable: Pageable
    ): Page<Orders> {
        return if (status != null) {
            orderRepository.findAllByUserIdAndStatus(userId, status, pageable)
        } else {
            orderRepository.findAllByUserId(userId, pageable)
        }
    }

    fun getOrderItemsGroupedByOrder(ordersPage: Page<Orders>): Map<UUID?, List<OrderItem>> {
        val orderIds = ordersPage.content.map { it.id!! }
        val allOrderItems = orderItemRepository.findAllByOrderId(orderIds)
        return allOrderItems.groupBy { it.order.id }
    }

    @Transactional
    fun cancelOrder(userId: UUID, orderId: UUID) {
        val order = orderRepository.findByIdWithLock(orderId)
            ?: throw OrderNotFoundException()

        if (order.user.id != userId) {
            throw OrderNotFoundException()
        }

        if (order.status != OrderStatus.PROCESSING) {
            throw OrderCannotBeCancelledException(order.status)
        }

        val orderItems = orderItemRepository.findAllByOrderId(listOf(orderId))
        orderItems.forEach { item ->
            val product = productRepository.findByIdWithLock(item.product.id!!)
            product?.let { product.stock += item.quantity }
        }

        order.status = OrderStatus.CANCELLED
    }
}
