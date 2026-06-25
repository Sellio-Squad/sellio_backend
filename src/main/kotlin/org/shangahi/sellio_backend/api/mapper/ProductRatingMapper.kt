package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.ProductRatingResponse
import org.shangahi.sellio_backend.entity.ProductRating
import java.time.Instant

fun ProductRating.toResponse(isOwnRating: Boolean = false): ProductRatingResponse {
    return ProductRatingResponse(
        id = id!!,
        userName = user.fullName,
        userAvatarUrl = user.avatarUrl,
        ratingValue = ratingValue,
        comment = comment,
        createdAt = createdAt ?: Instant.now(),
        deletable = isOwnRating,
        editable = isOwnRating
    )
}
