package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ResetPasswordRequest(
    @field:NotBlank
    val sessionId: String,

    @field:NotBlank @field:Size(min = 8)
    val newPassword: String,

    @field:NotBlank @field:Size(min = 8)
    val confirmPassword: String
)
