package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.UUID

data class VerifyForgotPasswordOtpRequest(
    @field:NotBlank(message = "OTP value must not be blank")
    @field:Length(min = 4, max = 4, message = "OTP must be 4 digits")
    val otp: String,

    @field:UUID(message = "must be in a valid UUID format for sessionId")
    val sessionId: String,
)
