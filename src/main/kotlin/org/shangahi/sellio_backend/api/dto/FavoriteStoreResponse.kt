package org.shangahi.sellio_backend.api.dto

import org.shangahi.sellio_backend.entity.Store
import org.shangahi.sellio_backend.entity.User
import java.time.Instant
import java.util.*

data class FavoriteStoreResponse(
    val id: UUID?,
    val storeId: UUID,
    val userId: UUID,
    val createdAt: Instant?
)