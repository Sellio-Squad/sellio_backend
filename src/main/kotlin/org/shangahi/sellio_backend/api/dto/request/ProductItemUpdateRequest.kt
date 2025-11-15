package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.Positive
import java.util.*

data class ProductItemUpdateRequest(
    @field:Positive(message = "Price must be greater than zero")
    val price: Double?,
    val discountId: UUID?,
    val colorId: Int?,
    val sizeId: Int?,
    @field:Positive(message = "Stock cannot be negative")
    val stock: Int?,
    val variationImageUrl: String?
)
