package org.shangahi.sellio_backend.api.dto.response

import java.time.Instant
import java.util.*

data class SubCategoryResponse(
    val id: UUID?,
    val title: String,
    val categoryId: UUID?,
    val categoryTitle: String?,
    val createdAt: Instant?,
    val updatedAt: Instant?
)