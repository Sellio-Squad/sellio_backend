package org.shangahi.sellio_backend.api.dto.response

data class CategorySectionResponse(
    val id: String,
    val sectionTitle: String,
    val categoryId: String,
    val subCategories: List<SubCategoryResponse>,
    val sortOrder: Int,
    val isActive: Boolean
)
