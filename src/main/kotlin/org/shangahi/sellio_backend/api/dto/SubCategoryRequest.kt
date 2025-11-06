package org.shangahi.sellio_backend.api.dto

import java.util.*

data class SubCategoryRequest(
    val title: String,
    val categoryId: UUID
)