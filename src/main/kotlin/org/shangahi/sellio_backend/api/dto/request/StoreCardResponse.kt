package org.shangahi.sellio_backend.api.dto.request

import java.util.UUID

data class StoreCardResponse(
    val id: UUID?,
    val title: String,
    val coverImageURL: String?,
    val maxDiscount: Double?,
    val isFavorite: Boolean
)

interface StoreDiscountStats {
    val storeId: UUID
    val maxDiscount: Double
}