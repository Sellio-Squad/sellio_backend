package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ProductRatingRequest(
    @field:Min(1)
    @field:Max(5)
    val ratingValue: Int,

    @field:NotBlank
    @field:Size(max = 500)
    val comment: String
)
