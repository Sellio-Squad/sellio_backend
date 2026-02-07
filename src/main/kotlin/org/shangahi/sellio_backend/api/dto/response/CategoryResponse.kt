package org.shangahi.sellio_backend.api.dto.response

import java.time.Instant
import java.util.*

data class CategoryResponse(
    val id: UUID,
    val title: String,
    val imageUrl: String?,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val subCategories: List<SubCategoryResponse> = emptyList()
)