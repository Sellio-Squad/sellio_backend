package org.shangahi.sellio_backend.api.dto.response

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class OrderItemResponse(
    val id: UUID?,
    val productId: UUID?,
    val productName: String,
    val productImageUrl: String?,
    val quantity: Int,
    val price: BigDecimal,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
