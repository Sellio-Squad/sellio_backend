package org.shangahi.sellio_backend.api.dto

import java.util.*

data class ProductResponse(
    val id: UUID,
    val title: String,
    val description: String?,
    val mainImageURL: String?,
    val storeId: UUID,
    val price: Double,
    val isUsed: Boolean,
    val isFeatured: Boolean,
    val subCategoryIds: List<UUID>,
    val imageUrls: List<String>,
    val items: List<ProductItemResponse>
)

data class ProductItemResponse(
    val id: UUID,
    val price: Double,
    val discountId: UUID?,
    val colorId: UUID?,
    val sizeId: UUID?,
    val weightId: Int?,
    val stock: Int
)