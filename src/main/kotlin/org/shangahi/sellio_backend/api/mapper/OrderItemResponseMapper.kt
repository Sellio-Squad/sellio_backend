package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.OrderItemResponse
import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.entity.OrderItem
import org.springframework.data.domain.Page

fun OrderItem.toResponse(): OrderItemResponse {
    return OrderItemResponse(
        id = id,
        productId = productItem.id,
        quantity = quantity,
        price = productItem.price,
        status = status,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Page<OrderItem>.toResponse(): PageResponse<OrderItemResponse> {
    return PageResponse(
        data = content.map { it.toResponse() },
        totalElements = totalElements,
        page = number,
        pageSize = size,
        totalPages = totalPages
    )
}