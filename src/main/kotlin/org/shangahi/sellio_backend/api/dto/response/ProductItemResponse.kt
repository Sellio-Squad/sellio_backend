package org.shangahi.sellio_backend.api.dto.response

import java.util.UUID

data class ProductItemResponse(
    val id: UUID,
    val price: Double?,
    val discountId: UUID?,
    val variationImageUrl: String?,
    val colorId: Int?,
    val sizeId: Int?,
    val stock: Int
)