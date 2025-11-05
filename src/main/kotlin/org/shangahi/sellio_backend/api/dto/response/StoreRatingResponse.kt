package org.shangahi.sellio_backend.api.dto.response

import java.util.*

data class StoreRatingResponse(
    val id: UUID,
    val averageRating: Double,
    val totalRatings: Long,
    val ratingCategorize: Map<Int, Long>
)

data class RatingBreakdown(
    val ratingValue: Int,
    val count: Long
)

data class RatingStats(
    val averageRating: Double,
    val totalRatings: Long
)
