package org.shangahi.sellio_backend.api.dto.response

import org.shangahi.sellio_backend.model.OrderStatus
import java.time.Instant
import java.util.UUID

data class OrderHistoryResponse(
    val orderId: UUID,
    val orderDate: Instant,
    val status: OrderStatus,
    val totalPrice: Double,
    val storeName: String,
    val storeLogoUrl: String?,
    val items: List<OrderItemResponse>
)