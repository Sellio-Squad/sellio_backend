package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank(message = "can not leave refreshToken blank")
    val refreshToken: String
)