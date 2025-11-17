package org.shangahi.sellio_backend.api.dto.response

import java.util.*

data class StoreInfoResponse(
    val id: UUID,
    val ownerId: UUID,
    val title: String,
    val description: String,
    val avatarImageURL: String?,
    val coverImageURL: String?,
    val phoneNumber: String?,
    val city: String,
    val government: String,
    val country: String,
    val featuredProducts: List<ProductCardResponse>,
    val avgRating: Double,
    val activeStoreDiscounts : List<StoreDiscountResponse>
)
