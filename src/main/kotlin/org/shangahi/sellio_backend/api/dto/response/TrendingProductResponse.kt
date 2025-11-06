package org.shangahi.sellio_backend.api.dto.response

import java.util.*

data class TrendingProductResponse(
    val id: UUID,
    val title: String,
    val sold: Long
)