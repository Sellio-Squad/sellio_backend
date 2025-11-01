package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.api.dto.TrendingProductResponse
import org.shangahi.sellio_backend.model.TrendingProduct
import org.springframework.data.domain.Page

fun TrendingProduct.toResponse(): TrendingProductResponse {
    return TrendingProductResponse(
        id = productId,
        title = productTitle,
        sold = totalSold
    )
}

fun Page<TrendingProduct>.toPagedResponse(): PageResponse<TrendingProductResponse> {
    return PageResponse(
        data = content.map { it.toResponse() },
        page = number,
        pageSize = size,
        totalElements = totalElements,
        totalPages = totalPages
    )
}