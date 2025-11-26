package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.OrderHistoryResponse
import org.shangahi.sellio_backend.entity.OrderItem
import org.shangahi.sellio_backend.entity.Orders


fun Orders.OrderHistoryResponse(orderItems: List<OrderItem>): OrderHistoryResponse {

    return OrderHistoryResponse(
        orderId = id!!,
        status = status,
        totalPrice = totalPrice,
        storeName = store.title,
        storeLogoUrl = store.avatarImageURL,
        orderDate = createdAt!!,
        items = orderItems.map { it.toResponse() }
    )
}