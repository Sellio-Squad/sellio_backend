package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.api.dto.StoreResponse
import org.shangahi.sellio_backend.entity.Store
import org.springframework.data.domain.Page

fun Store.toStoreResponse(): StoreResponse {
    return StoreResponse(
        id = id,
        title = title,
        city = city,
        government = government,
        country = country,
        avatarImageURL = avatarImageURL,
        coverImageURL = coverImageURL
    )
}


fun Page<Store>.toResponse(): PageResponse<StoreResponse> {
    return PageResponse(
        data = this.content.map { it.toStoreResponse() },
        totalElements = this.totalElements,
        page = this.number,
        pageSize = this.size,
        totalPages = this.totalPages
    )
}