package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.springframework.data.domain.Page

fun <T, R> Page<T>.toPageResponse(mapper: (T) -> R): PageResponse<R> {
    return PageResponse(
        data = this.content.map(mapper),
        totalElements = this.totalElements,
        page = this.number,
        pageSize = this.size,
        totalPages = this.totalPages
    )
}