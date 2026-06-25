package org.shangahi.sellio_backend.api.dto.response

import java.time.Instant
import java.util.*

data class ProductRatingResponse(
    val id: UUID,
    val userName: String,
    val userAvatarUrl: String?,
    val ratingValue: Int,
    val comment: String,
    val createdAt: Instant,
    val deletable: Boolean,
    val editable: Boolean
)

data class ProductRatingSummaryResponse(
    val productId: UUID,
    val averageRating: Double,
    val totalRatings: Long,
    val ratingCategorize: Map<Int, Long>,
    val canReview: Boolean,
    val userReview: ProductRatingResponse?,
    val recentReviews: List<ProductRatingResponse>
)
