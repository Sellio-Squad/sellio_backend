package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.OrderItemResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.entity.OrderItem
import org.springframework.data.domain.Page
import java.math.BigDecimal
import java.math.RoundingMode

fun OrderItem.toResponse(): OrderItemResponse {
    val imageUrl = this.customizationImageUrl
        ?: this.product.mainImageURL
    return OrderItemResponse(
        id = id,
        productId = product.id,
        quantity = quantity,
        productName = product.title,
        productImageUrl = imageUrl,
        price = BigDecimal.valueOf(product.price).setScale(2, RoundingMode.HALF_UP),
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
