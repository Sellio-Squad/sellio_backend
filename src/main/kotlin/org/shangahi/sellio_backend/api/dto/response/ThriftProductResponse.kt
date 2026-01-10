package org.shangahi.sellio_backend.api.dto.response

import java.util.*

data class ThriftProductResponse(
    val id: UUID,
    val title: String,
    val description: String?,
    val mainImageURL: String?,
    val storeId: UUID,
    val price: Double,
    val isUsed: Boolean = true,
    val isFeatured: Boolean,
    val isFavorite: Boolean,
    val subCategoryIds: List<UUID>,
    val imageUrls: List<String>,
    val items: List<ProductItemResponse>,
    val condition: String,
    val defects: String?
)



data class ThriftProductCardResponse(
    val id: UUID,
    val title: String,
    val price: Double,
    val mainImageUrl: String?,
    val isFavorite: Boolean,
    val condition: String
)