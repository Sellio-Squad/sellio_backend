package org.shangahi.sellio_backend.api.dto.request

import java.util.UUID

data class ConfirmOrderRequest(
    val note: String? = null,
    val items: List<OrderItemRequest>
)

data class OrderItemRequest(
    val productItemId: UUID,
    val quantity: Int,
    val customizationImageUrl: String? = null
)