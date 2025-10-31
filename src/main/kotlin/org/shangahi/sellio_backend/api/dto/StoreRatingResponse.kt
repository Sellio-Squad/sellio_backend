package org.shangahi.sellio_backend.api.dto

import java.util.UUID

data class StoreRatingResponse(
    val id: UUID,
    val averageRating: Double,
    val totalRatings: Long,
    val ratingCategorize : Map<Int,Long>
    )
