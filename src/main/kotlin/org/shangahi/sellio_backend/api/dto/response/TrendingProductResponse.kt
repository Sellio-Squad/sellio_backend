package org.shangahi.sellio_backend.api.dto.response

import java.util.UUID

data class TrendingProductResponse(
    val id: UUID,
    val title: String,
    val sold: Long,
    val minPrice: Double?,
    val image: String?,
    val isFavorite: Boolean
)