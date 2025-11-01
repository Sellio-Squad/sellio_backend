package org.shangahi.sellio_backend.model

import java.util.UUID

data class TrendingProduct(
    val productId: UUID,
    val productTitle: String,
    val productDescription: String?,
    val storeId: UUID,
    val totalSold: Long
)