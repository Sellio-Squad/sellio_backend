package org.shangahi.sellio_backend.api.dto.response

import org.shangahi.sellio_backend.model.OrderStatus
import java.time.Instant
import java.util.*

data class OrderItemResponse(
    val id: UUID?,
    val productId: UUID?,
    val quantity: Int,
    val price: Double,
    val status: OrderStatus,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
