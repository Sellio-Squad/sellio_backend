package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.CartItemResponse
import org.shangahi.sellio_backend.api.dto.response.CartResponse
import org.shangahi.sellio_backend.entity.Cart
import org.shangahi.sellio_backend.entity.CartItem
import java.math.BigDecimal
import java.math.RoundingMode

fun Cart.toResponse(): CartResponse {
    val items = cartItems.map { it.toResponse() }
    return CartResponse(
        id = id!!,
        items = items,
        totalPrice = items.fold(BigDecimal.ZERO) { acc, item -> acc + item.totalPrice },
        itemCount = items.sumOf { it.quantity }
    )
}

fun CartItem.toResponse(): CartItemResponse {
    val unitPrice = BigDecimal.valueOf(product.price).setScale(2, RoundingMode.HALF_UP)
    return CartItemResponse(
        id = id!!,
        productId = product.id!!,
        productTitle = product.title,
        productImage = product.mainImageURL,
        unitPrice = unitPrice,
        quantity = quantity,
        totalPrice = unitPrice * BigDecimal.valueOf(quantity.toLong())
    )
}
