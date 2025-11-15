package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.CategoryRequest
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.entity.Category

fun Category.toResponse(): CategoryResponse {
    return CategoryResponse(
        id = this.id!!,
        title = this.title,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        subCategories = this.subCategories.map { it.toResponse() }
    )
}

fun CategoryRequest.toEntity(): Category = Category(title = this.title)