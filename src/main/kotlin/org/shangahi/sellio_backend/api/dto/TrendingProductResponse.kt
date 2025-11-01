package org.shangahi.sellio_backend.api.dto

import java.util.UUID

data class TrendingProductResponse(
    val id: UUID,
    val title: String,
    val sold: Long
)