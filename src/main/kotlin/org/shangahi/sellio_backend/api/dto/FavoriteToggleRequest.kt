package org.shangahi.sellio_backend.api.dto

import java.util.*

data class FavoriteToggleRequest(
    val productId: UUID,
    val userId: UUID,
)