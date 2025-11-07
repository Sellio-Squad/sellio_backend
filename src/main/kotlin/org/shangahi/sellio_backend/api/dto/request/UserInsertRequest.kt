package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserInsertRequest(
    @field:NotBlank(message = "First name is required")
    val firstName: String,

    @field:NotBlank(message = "Last name is required")
    val lastName: String,

    @field:NotBlank(message = "Phone number is required")
    //@field:Pattern(regexp = "^\\d{11}$", message = "Phone number must be exactly 11 digits")
    val phoneNumber: String,

    @field:Email(message = "Invalid email format")
    val email: String?,

    @field:NotBlank(message = "City is required")
    val city: String,

    @field:NotBlank(message = "Country is required")
    val country: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,

    val avatarUrl: String?
)