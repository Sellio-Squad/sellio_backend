package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.springframework.web.multipart.MultipartFile
import java.util.*

data class CreateStoreRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,
    @field:NotBlank(message = "Description can not be null")
    val description: String,
    @field:NotBlank(message = "City can not be null")
    val city: String,
    @field:NotBlank(message = "Country can not be null")
    val country: String,
    @field:NotEmpty(message = "At least one category is required")
    val categoryIds: List<UUID>,
    @field:NotBlank(message = "avatarImage can not be null")
    val avatarImage: MultipartFile?,
    @field:NotBlank(message = "coverImage can not be null")
    val coverImage: MultipartFile?
)
