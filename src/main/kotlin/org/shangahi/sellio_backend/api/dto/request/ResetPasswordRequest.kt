package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.UUID

data class ResetPasswordRequest(
    @field:NotBlank(message = "sessionId must not be blank")
    @field:UUID(message = "must be in a valid UUID format for sessionId")
    val sessionId: String,

    @field:NotBlank @field:Size(min = 8, message = "newPassword must be at least 8 characters long")
    val newPassword: String,
)
