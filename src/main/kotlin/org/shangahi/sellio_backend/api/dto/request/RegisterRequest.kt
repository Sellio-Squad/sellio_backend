package org.shangahi.sellio_backend.api.dto.request

import java.util.UUID

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val password: String,
    val city: String,
    val country: String,
    val avatarUrl: String?,
    val email: String?,
    val sessionId: UUID?
)
