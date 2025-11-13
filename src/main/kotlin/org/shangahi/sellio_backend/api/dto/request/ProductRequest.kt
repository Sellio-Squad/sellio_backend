package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.util.*

data class ProductRequest(
    val title: String,
    val description: String?,
    val mainImageURL: String?,
    val storeId: UUID,
    val subCategoryIds: List<UUID> = emptyList(),
    val imageUrls: List<String> = emptyList(),
    val items: List<ProductItemRequest> = emptyList(),
    val price: Double,
    val isUsed: Boolean = false,
    val isFeatured: Boolean = false
)

data class ProductItemRequest(
    @field:NotNull(message = "Price is required for an item")
    @field:Positive(message = "Price must be greater than zero")
    val price: Double,
    val discountId: UUID? = null,
    val colorId: UUID? = null,
    val sizeId: UUID? = null,
    @field:Positive(message = "weight must be greater than zero")
    val weightId: Int? = null,
    @field:NotNull(message = "stock is required for an item")
    @field:Positive(message = "stock must be greater than zero")
    val stock: Int,
    val variationImageUrl: String? = null
)