package org.shangahi.sellio_backend.api.dto.request

data class VerifyOtpRequest(
    val otp: String,
    val sessionId: String
)