package org.shangahi.sellio_backend.api.dto.request

data class LoginRequest(
    val phoneNumber: String,
    val password: String
)
