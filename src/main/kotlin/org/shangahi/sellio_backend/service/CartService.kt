package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.AddCartItemRequest
import org.shangahi.sellio_backend.api.dto.request.UpdateCartItemRequest
import org.shangahi.sellio_backend.api.dto.response.CartResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.entity.Cart
import org.shangahi.sellio_backend.entity.CartItem
import org.shangahi.sellio_backend.repository.CartItemRepository
import org.shangahi.sellio_backend.repository.CartRepository
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun getOrCreateCart(userId: UUID): CartResponse {
        val cart = cartRepository.findByUserId(userId)
            ?: createNewCart(userId)
        return cart.toResponse()
    }

    @Transactional
    fun addItem(userId: UUID, request: AddCartItemRequest): CartResponse {
        val cart = getCartForUser(userId)
        val product = productRepository.findByIdOrNull(request.productId)
            ?: throw ProductNotFoundException()

        if (request.quantity <= 0) {
            throw CartItemInvalidQuantityException()
        }
        if (request.quantity > product.stock) {
            throw CartItemQuantityExceedsStockException(product.stock)
        }

        val existingItem = cartItemRepository.findByCartIdAndProductId(cart.id!!, product.id!!)
        if (existingItem != null) {
            val newQuantity = existingItem.quantity + request.quantity
            if (newQuantity > product.stock) {
                throw CartItemQuantityExceedsStockException(product.stock)
            }
            existingItem.quantity = newQuantity
            cartItemRepository.save(existingItem)
        } else {
            val cartItem = CartItem(
                cart = cart,
                product = product,
                quantity = request.quantity
            )
            cartItemRepository.save(cartItem)
        }

        return cartRepository.findByUserId(userId)!!.toResponse()
    }

    @Transactional
    fun updateItemQuantity(userId: UUID, itemId: UUID, request: UpdateCartItemRequest): CartResponse {
        val cart = getCartForUser(userId)
        val item = cartItemRepository.findByIdOrNull(itemId)
            ?: throw CartItemNotFoundException()

        if (item.cart.id != cart.id) {
            throw CartItemNotFoundException()
        }

        if (request.quantity <= 0) {
            throw CartItemInvalidQuantityException()
        }
        if (request.quantity > item.product.stock) {
            throw CartItemQuantityExceedsStockException(item.product.stock)
        }

        item.quantity = request.quantity
        cartItemRepository.save(item)

        return cartRepository.findByUserId(userId)!!.toResponse()
    }

    @Transactional
    fun removeItem(userId: UUID, itemId: UUID): CartResponse {
        val cart = getCartForUser(userId)
        val item = cartItemRepository.findByIdOrNull(itemId)
            ?: throw CartItemNotFoundException()

        if (item.cart.id != cart.id) {
            throw CartItemNotFoundException()
        }

        cartItemRepository.delete(item)

        return cartRepository.findByUserId(userId)!!.toResponse()
    }
    private fun createNewCart(userId: UUID): Cart {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()
        return cartRepository.save(Cart(user = user))
    }

    private fun getCartForUser(userId: UUID): Cart {
        return cartRepository.findByUserId(userId)
            ?: throw CartNotFoundException()
    }
}
