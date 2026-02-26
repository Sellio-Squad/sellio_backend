package org.shangahi.sellio_backend.api.dto.otp

data class SendOtpRequest(
    val phone: String,
    val message: String,
    val otp: String
)