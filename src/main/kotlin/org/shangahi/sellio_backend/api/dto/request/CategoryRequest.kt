package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank

data class CategoryRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,
)


data class EditCategoryRequest(
    val title: String?,
)