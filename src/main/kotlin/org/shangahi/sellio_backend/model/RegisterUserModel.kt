package org.shangahi.sellio_backend.model

import java.util.UUID

data class RegisterUserModel(
    val phoneNumber: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val city: String,
    val country: String,
    val avatarUrl: String?,
    val email: String?,
    val sessionId: UUID?
)