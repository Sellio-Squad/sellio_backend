package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.FavoriteStoreResponse
import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.entity.FavoriteStore
import org.springframework.data.domain.Page
import java.time.Instant

fun FavoriteStore.toResponse(): FavoriteStoreResponse {
    return FavoriteStoreResponse(
        id = id,
        storeId = store.id?: throw IllegalStateException("Store ID was null for Store ${this.store.title}"),
        userId = user.id?: throw IllegalStateException("User ID was null for User ${this.user.id}"),
        createdAt = createdAt
    )
}

fun Page<FavoriteStore>.toPageResponse(): PageResponse<FavoriteStoreResponse> {
    return PageResponse(
        data = content.map { it.toResponse() },
        totalElements = totalElements,
        page = number,
        pageSize = size,
        totalPages = totalPages
    )
}