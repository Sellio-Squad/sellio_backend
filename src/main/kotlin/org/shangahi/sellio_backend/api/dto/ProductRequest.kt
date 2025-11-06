package org.shangahi.sellio_backend.api.dto

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
    val price: Double,
    val discountId: UUID?,
    val colorId: UUID?,
    val sizeId: UUID?,
    val weightId: Int?,
    val stock: Int
)