package org.shangahi.sellio_backend.api.dto.request

data class OfferRequest(
    val title: String?,
    val actionType: String,
    val actionId: String?,
    val startDate: Long?,
    val endDate: Long?,
    val priority: Int = 0
)