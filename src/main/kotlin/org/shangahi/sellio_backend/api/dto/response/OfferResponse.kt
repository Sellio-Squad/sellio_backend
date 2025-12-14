package org.shangahi.sellio_backend.api.dto.response

import java.time.Instant

data class OfferResponse(
    val id: Long,
    val imageUrl: String,
    val title: String?,
    val actionType: String,
    val actionId: String?,
    val startedDate: Instant?,
    val endDate :Instant?,
)

