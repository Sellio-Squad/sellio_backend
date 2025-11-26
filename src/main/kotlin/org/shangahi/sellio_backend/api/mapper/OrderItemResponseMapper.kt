package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.OrderItemResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.entity.OrderItem
import org.springframework.data.domain.Page

fun OrderItem.toResponse(): OrderItemResponse {
    val price = productItem.price ?: productItem.product.price
    return OrderItemResponse(
        id = id,
        productId = productItem.id,
        quantity = quantity,
        price = price,
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