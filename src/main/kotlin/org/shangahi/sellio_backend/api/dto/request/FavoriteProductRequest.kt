package org.shangahi.sellio_backend.api.dto.request

import java.util.UUID

data class FavoriteProductRequest (
    val productId: UUID,
    val userId: UUID,
)