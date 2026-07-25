package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.util.*

data class ProductItemUpdateRequest(
    @field:Positive(message = "Price must be greater than zero")
    @field:Digits(integer = 10, fraction = 2)
    val price: BigDecimal?,
    val discountId: UUID?,
    val colorId: Int?,
    val sizeId: Int?,
    @field:Positive(message = "Stock cannot be negative")
    val stock: Int?,
    val variationImageUrl: String?
)
