package org.shangahi.sellio_backend.api.dto.request

import java.util.UUID

data class FavoriteToggleRequest (
    val productId: UUID,
    val userId: UUID,
)