package org.shangahi.sellio_backend.api.dto

import org.shangahi.sellio_backend.model.OrderStatus
import java.time.Instant
import java.util.UUID

data class OrderItemResponse(
    val id: UUID?,
    val productId: UUID?,
    val quantity: Int,
    val price: Double,
    val status: OrderStatus,
    val createdAt: Instant?,
    val updatedAt: Instant?
)
