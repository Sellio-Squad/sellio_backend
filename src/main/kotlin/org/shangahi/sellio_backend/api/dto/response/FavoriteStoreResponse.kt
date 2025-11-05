package org.shangahi.sellio_backend.api.dto.response

import java.time.Instant
import java.util.*

data class FavoriteStoreResponse(
    val id: UUID?,
    val storeId: UUID,
    val userId: UUID,
    val createdAt: Instant?
)