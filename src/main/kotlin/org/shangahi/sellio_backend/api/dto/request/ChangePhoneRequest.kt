package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank

data class ChangePhoneRequest(
    @field:NotBlank(message = "New phone number is required")
    val phoneNumber: String,
    @field:NotBlank(message = "Region is required")
    val region: String,
    )