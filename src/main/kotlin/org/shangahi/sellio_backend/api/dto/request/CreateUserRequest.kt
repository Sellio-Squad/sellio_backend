package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.UUID

data class CreateUserRequest(
    @field:NotBlank(message = "First name is required")
    val firstName: String,

    @field:NotBlank(message = "Last name is required")
    val lastName: String,

    @field:NotBlank(message = "Phone number is required")
    val phoneNumber: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,

    @field:NotBlank(message = "City is required")
    val city: String,

    @field:NotBlank(message = "Country is required")
    val country: String,
    val avatarUrl: String?,
    val email: String?,

    @field:NotBlank(message = "Session ID is required")
    val sessionId: UUID?,
)
