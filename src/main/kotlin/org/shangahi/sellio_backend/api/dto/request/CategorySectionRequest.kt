package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class CategorySectionRequest(
    @field:NotBlank(message = "sectionTitle can not be blank")
    val sectionTitle: String,
    @field:NotBlank(message = "categoryId can not be blank")
    val categoryId: String,
    @field:Positive(message = "sortOrder must be greater than zero")
    val sortOrder: Int,
    val isActive: Boolean = true
)

data class EditCategorySectionRequest(
    val sectionTitle: String,
    val categoryId: String,
    val sortOrder: Int,
    val isActive: Boolean = true
)
