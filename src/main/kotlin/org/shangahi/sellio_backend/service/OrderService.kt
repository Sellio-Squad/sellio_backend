package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.ConfirmOrderRequest
import org.shangahi.sellio_backend.api.dto.request.OrderItemRequest
import org.shangahi.sellio_backend.entity.*
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.repository.OrderItemRepository
import org.shangahi.sellio_backend.repository.OrderRepository
import org.shangahi.sellio_backend.repository.ProductItemRepository
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.ProductItemNotEnoughStockException
import org.shangahi.sellio_backend.service.exception.ProductItemNotFoundException
import org.shangahi.sellio_backend.service.exception.ProductItemOutOfStockException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class OrderService(
    private val orderItemRepository: OrderItemRepository,
    private val userRepository: UserRepository,
    private val productItemRepository: ProductItemRepository,
    private val orderRepository: OrderRepository
) {
    fun getCompletedOrdersItems(pageable: Pageable): Page<OrderItem> {
        return orderItemRepository.findAllByStatus(pageable = pageable, status = OrderStatus.COMPLETED)
    }

    @Transactional
    fun confirmOrder(userId: UUID, request: ConfirmOrderRequest): List<UUID> {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException()
        val requestItemsMap = request.items.associateBy { it.productItemId }
        val productItemIds = request.items.map { it.productItemId }
        val productItems: List<ProductItem> = productItemRepository.findAllById(productItemIds)
        if (productItems.size != productItemIds.size) {
            throw ProductItemNotFoundException()
        }
        val itemsByStore = productItems.groupBy { it.product.store }
        val createdOrderIds = orderProcessing(user, request, requestItemsMap, itemsByStore)
        return createdOrderIds
    }

    @Transactional(readOnly = true)
    fun getOrders(
        userId: UUID,
        status: OrderStatus?,
        pageable: Pageable
    ): Page<Orders> {

        val ordersPage = if (status != null) {
            orderRepository.findAllByUserIdAndStatus(userId, status, pageable)
        } else {
            orderRepository.findAllByUserId(userId, pageable)
        }
        if (ordersPage.isEmpty) {
            return Page.empty()
        }

        return ordersPage
    }

    fun groupedItemsByOrder(ordersPage: Page<Orders>): Map<UUID?, List<OrderItem>> {
        val orderIds = ordersPage.content.map { it.id!! }
        val allOrderItems = orderItemRepository.findAllByOrderId(orderIds)
        return allOrderItems.groupBy { it.order.id }
    }

    private fun orderProcessing(
        user: User,
        request: ConfirmOrderRequest,
        requestItemsMap: Map<UUID, OrderItemRequest>,
        itemsByStore: Map<Store, List<ProductItem>>
    ): List<UUID> {
        val createdOrderIds = mutableListOf<UUID>()
        itemsByStore.forEach { store, storeProductItems ->
            val order = Orders(
                user = user,
                store = store,
                note = request.note,
                status = OrderStatus.PROCESSING,
                totalPrice = 0.0
            )
            var orderTotal = 0.0
            val orderItemsEntities = mutableListOf<OrderItem>()
            storeProductItems.forEach { item ->
                val requestItem = requestItemsMap[item.id] ?: throw ProductItemNotFoundException()
                if (item.stock == 0) {
                    throw ProductItemOutOfStockException()
                }
                if (item.stock < requestItem.quantity) {
                    throw ProductItemNotEnoughStockException(requestItem.quantity, item.stock)
                }
                val updatedStockItem = item.copy(stock = item.stock - requestItem.quantity)
                productItemRepository.save(updatedStockItem)

                val itemTotal = item.price * requestItem.quantity
                orderTotal += itemTotal

                orderItemsEntities.add(
                    OrderItem(
                        productItem = item,
                        order = order,
                        quantity = requestItem.quantity,
                        customizationImageUrl = requestItem.customizationImageUrl,
                        status = OrderStatus.PROCESSING
                    )
                )
            }
            orderRepository.save(order)
            orderItemRepository.saveAll(orderItemsEntities)
            orderRepository.save(
                order.copy(
                    totalPrice = orderTotal
                )
            )

            createdOrderIds.add(order.id!!)
        }
        return createdOrderIds
    }

}
