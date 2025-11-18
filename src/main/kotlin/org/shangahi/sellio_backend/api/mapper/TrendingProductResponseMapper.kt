package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.TrendingProductResponse
import org.shangahi.sellio_backend.entity.Product
import org.shangahi.sellio_backend.model.TrendingProduct
import org.springframework.data.domain.Page

fun TrendingProduct.toResponse(): TrendingProductResponse {
    return TrendingProductResponse(
        id = productId,
        title = productTitle.orEmpty(),
        sold = totalSold,
        price = price?:0.0,
        image = mainImageURL.orEmpty()
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