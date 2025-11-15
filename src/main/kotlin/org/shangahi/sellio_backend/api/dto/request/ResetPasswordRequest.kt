package org.shangahi.sellio_backend.api.dto.request

data class ResetPasswordRequest(
    val sessionId: String,
    val newPassword: String,
    val confirmPassword: String
)