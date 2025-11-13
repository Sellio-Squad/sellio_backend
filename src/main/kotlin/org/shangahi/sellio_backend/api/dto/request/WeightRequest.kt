package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank

data class WeightRequest(
    @field:NotBlank(message = "value must be not empty")
    val value: Double,
)
