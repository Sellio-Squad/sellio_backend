package org.shangahi.sellio_backend.api.dto.request

import jakarta.validation.constraints.Positive
import java.util.*

data class ProductUpdateRequest(
    val title: String?,
    val description: String?,
    val mainImageURL: String?,
    val storeId: UUID?,
    val subCategoryIds: List<UUID>?,
    val imageUrls: List<String>?,
    val items: List<ProductItemRequest>?,
    @field:Positive(message = "Price must be greater than zero")
    val price: Double?,
    val isUsed: Boolean?,
    val isFeatured: Boolean?
)
