package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "phoneNumber can not be blank")
    val phoneNumber: String,
    @field:Size(min = 8, message = "password must be at least 8 characters long")
    val password: String
)
