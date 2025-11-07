package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.entity.SubCategory

fun SubCategory.toResponse(): SubCategoryResponse {
    return SubCategoryResponse(
        id = this.id!!,
        title = this.title,
        categoryId = this.category.id,
        categoryTitle = this.category.title,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}