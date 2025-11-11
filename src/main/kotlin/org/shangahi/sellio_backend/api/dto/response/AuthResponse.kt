package org.shangahi.sellio_backend.api.dto.response

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)
