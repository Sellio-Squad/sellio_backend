package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.util.*

data class ProductRequest(
    val title: String,
    val description: String?,
    val mainImageURL: String?,
    val storeId: UUID,
    @field:NotEmpty(message = "Product must have at least one sub-category")
    val subCategoryIds: List<UUID> = emptyList(),
    val imageUrls: List<String> = emptyList(),
    @field:NotEmpty(message = "At least one item variation is required (for stock)")
    val items: List<ProductItemRequest> = emptyList(),
    @field:Positive(message = "Price must be greater than zero")
    val price: Double,
    val isUsed: Boolean = false,
    val isFeatured: Boolean = false
)

data class ProductItemRequest(
    @field:Positive(message = "Price must be greater than zero")
    val price: Double,
    val discountId: UUID? = null,
    val colorId: Int? = null,
    val sizeId: Int? = null,
    @field:Positive(message = "weight must be greater than zero")
    val weightId: Int? = null,
    @field:NotNull(message = "stock is required for an item")
    @field:Positive(message = "stock must be greater than zero")
    val stock: Int,
    val variationImageUrl: String? = null
)