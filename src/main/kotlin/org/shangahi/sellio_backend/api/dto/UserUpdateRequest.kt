package org.shangahi.sellio_backend.api.dto

import java.util.*

data class UserUpdateRequest(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String?,
    val city: String,
    val country: String,
    val password: String,
    val avatarUrl: String?,
)
