package org.shangahi.sellio_backend.api.dto.response

import java.math.BigDecimal
import java.util.*

data class ProductResponse(
    val id: UUID,
    val title: String,
    val description: String?,
    val mainImageURL: String?,
    val storeId: UUID,
    val minPrice: BigDecimal?,
    val isUsed: Boolean,
    val isFeatured: Boolean,
    val isFavorite: Boolean,
    val subCategoryIds: List<UUID>,
    val imageUrls: List<String>,
    val items: List<ProductItemResponse>,
    val price: BigDecimal,
    val stock: Int
)