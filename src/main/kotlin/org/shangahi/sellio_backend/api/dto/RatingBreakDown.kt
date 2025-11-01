package org.shangahi.sellio_backend.api.dto

data class RatingBreakdown (
    val ratingValue: Int,
    val count: Long
)
data class  RatingStats (
    val averageRating: Double,
    val totalRatings: Long
)
