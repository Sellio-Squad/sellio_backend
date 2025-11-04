package org.shangahi.sellio_backend.api.dto.response

data class PageResponse<T>(
    val data: List<T>,
    val totalElements: Long,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int
)