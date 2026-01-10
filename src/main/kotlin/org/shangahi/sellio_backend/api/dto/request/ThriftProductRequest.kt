package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.util.*

data class ThriftProductRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,

    val description: String?,

    val mainImageURL: String?,

    @field:NotNull(message = "Store ID is required")
    val storeId: UUID,

    @field:NotEmpty(message = "Product must have at least one sub-category")
    val subCategoryIds: List<UUID> = emptyList(),

    val imageUrls: List<String> = emptyList(),

    @field:Positive(message = "Price must be greater than zero")
    val price: Double,

    @field:NotBlank(message = "Condition is required for thrift items")
    val condition: String,

    val defects: String?,

    @field:NotEmpty(message = "At least one item variation is required (for stock)")
    val items: List<ProductItemRequest>
)