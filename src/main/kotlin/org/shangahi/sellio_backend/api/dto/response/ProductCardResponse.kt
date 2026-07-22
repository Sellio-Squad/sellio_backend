package org.shangahi.sellio_backend.api.dto.response

import java.math.BigDecimal
import java.util.*

data class ProductCardResponse(
    val id: UUID,
    val title: String,
    val description: String,
    val minPrice: BigDecimal?,
    val mainImageUrl: String?,
    val subCategoriesIds: List<UUID>,
    val isFavorite: Boolean
)