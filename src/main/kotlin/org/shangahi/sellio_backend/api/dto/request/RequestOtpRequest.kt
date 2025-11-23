package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank

data class RequestOtpRequest(
    @field:NotBlank(message = "phoneNumber must not be blank")
    val phoneNumber: String,
    @field:NotBlank(message = "defaultRegion must not be blank")
    val defaultRegion: String
)