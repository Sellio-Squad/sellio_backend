package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import java.util.*

data class SubCategoryRequest(
    @NotBlank(message = "Title is required")
    val title: String,
    @NotBlank(message = "Category ID is required")
    val categoryId: UUID
)

data class EditSubCategoryRequest(
    val title: String?,
    val categoryId: UUID?
)