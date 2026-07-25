package org.shangahi.sellio_backend.api.dto.response

import java.math.BigDecimal
import java.util.*

data class CartResponse(
    val id: UUID,
    val items: List<CartItemResponse>,
    val totalPrice: BigDecimal,
    val itemCount: Int
)

data class CartItemResponse(
    val id: UUID,
    val productId: UUID,
    val productTitle: String,
    val productImage: String?,
    val unitPrice: BigDecimal,
    val quantity: Int,
    val totalPrice: BigDecimal
)
