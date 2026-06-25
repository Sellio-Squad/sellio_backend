package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import org.shangahi.sellio_backend.model.Role

data class RefreshTokenRequest(
    @field:NotBlank(message = "refreshToken can not be blank")
    val refreshToken: String,
    @field:NotBlank(message = "Role can not be null")
    val role: Role
)