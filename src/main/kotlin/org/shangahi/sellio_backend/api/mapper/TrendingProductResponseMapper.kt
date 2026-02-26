package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.TrendingProductResponse
import org.shangahi.sellio_backend.model.TrendingProduct
import org.springframework.data.domain.Page
import java.util.UUID

fun TrendingProduct.toResponse(isFavorite: Boolean): TrendingProductResponse {
    return TrendingProductResponse(
        id = productId,
        title = productTitle.orEmpty(),
        sold = totalSold,
        minPrice = minPrice,
        image = mainImageURL.orEmpty(),
        isFavorite = isFavorite
    )
}


fun Page<TrendingProduct>.toPagedResponse(
    favoriteProductIds: Set<UUID>

): PageResponse<TrendingProductResponse> {
    return PageResponse(
        data =content.map {
            it.toResponse(
                isFavorite = favoriteProductIds.contains(it.productId)
            )
        },
        page = number,
        pageSize = size,
        totalElements = totalElements,
        totalPages = totalPages
    )
}