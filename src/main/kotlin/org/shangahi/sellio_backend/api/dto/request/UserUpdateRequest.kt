package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.Email

data class UserUpdateRequest(
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?,
    @field:Email(message = "Invalid email format")
    val email: String?,
    val city: String?,
    val country: String?,
    val avatarUrl: String?,
)
