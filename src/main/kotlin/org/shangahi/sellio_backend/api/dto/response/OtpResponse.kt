package org.shangahi.sellio_backend.api.dto.response

data class OtpResponse(
    val sessionId: String,
    val otp: String,
    val message: String
)
