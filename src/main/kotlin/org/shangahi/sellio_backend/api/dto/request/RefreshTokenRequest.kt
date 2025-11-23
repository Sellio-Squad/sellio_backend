package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank(message = "refreshToken can not be blank")
    val refreshToken: String
)