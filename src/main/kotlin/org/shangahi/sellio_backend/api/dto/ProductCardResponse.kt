package org.shangahi.sellio_backend.api.dto

import java.util.UUID

data class ProductCardResponse(
    val id: UUID,
    val title: String,
    val price: Double,
    val mainImageUrl: String?
)