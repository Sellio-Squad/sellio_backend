package org.shangahi.sellio_backend.api.dto

import java.time.Instant
import java.util.UUID

data class DiscountResponse(
    val id: UUID,
    val storeId: UUID?,
    val productId: UUID?,
    val subCategoryId: UUID?,
    val type: String,
    val value: Double,
    val startDate: Instant?,
    val endDate: Instant?
)
