package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateStoreRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,
    @field:NotBlank(message = "Description can not be blank")
    val description: String,
    @field:NotBlank(message = "Phone number can not be blank")
    val phoneNumber: String?,
    @field:NotBlank(message = "City can not be blank")
    val city: String,
    @field:NotBlank(message = "Government can not be blank")
    val government: String,
    @field:NotBlank(message = "Country can not be blank")
    val country: String,
)
