package org.shangahi.sellio_backend.api.dto.response

import java.time.Instant
import java.util.*

data class FavoriteProductsResponse(
    val id: UUID,
    val productId: UUID,
    val userId: UUID,
    val createdAt: Instant
)
