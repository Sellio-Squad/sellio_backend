package org.shangahi.sellio_backend.api.dto.response

import java.math.BigDecimal
import java.util.UUID

data class ProductItemResponse(
    val id: UUID,
    val price: BigDecimal?,
    val discountId: UUID?,
    val variationImageUrl: String?,
    val colorId: Int?,
    val sizeId: Int?,
    val stock: Int
)