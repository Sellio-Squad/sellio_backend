package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.SubCategoryRequest
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.entity.Category
import org.shangahi.sellio_backend.entity.SubCategory
import java.util.Collections.emptySet

fun SubCategory.toResponse(): SubCategoryResponse {
    return SubCategoryResponse(
        id = this.id!!,
        title = this.title,
        imageUrl = this.imageUrl,
        categoryId = this.category.id,
        categoryTitle = this.category.title,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun SubCategoryRequest.toEntity(category: Category): SubCategory {
    return SubCategory(
        title = this.title,
        category = category,
        products = emptySet()
    )
}