package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.Positive
import java.util.*

data class AddCartItemRequest(
    val productId: UUID,
    @field:Positive(message = "Quantity must be greater than zero")
    val quantity: Int
)

data class UpdateCartItemRequest(
    @field:Positive(message = "Quantity must be greater than zero")
    val quantity: Int
)
