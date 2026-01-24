package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class UserUpdateRequest(
    @field:Size(min = 2, max = 50)
    val fullName: String?,
    @field:Email(message = "Invalid email format")
    @field:Size(min = 2, max = 100)
    val email: String?,
    @field:Size(min = 2, max = 60)
    val city: String?,
    @field:Size(min = 2, max = 60)
    val country: String?,
)
