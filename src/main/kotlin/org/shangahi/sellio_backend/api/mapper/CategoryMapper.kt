package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.CategoryRequest
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.entity.Category

fun Category.toDTO(): CategoryResponse {
    return CategoryResponse(
        id = this.id!!,
        title = this.title,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        subCategories = this.subCategories.map { it.toDTO() }
    )
}

fun CategoryRequest.toEntity(): Category = Category(title = this.title)