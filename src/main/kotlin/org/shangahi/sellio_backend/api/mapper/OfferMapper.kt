package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.OfferResponse
import org.shangahi.sellio_backend.entity.Offer

fun Offer.toResponse(): OfferResponse {
    return OfferResponse(
        id = id,
        imageUrl = imageUrl,
        title = title,
        actionType = actionType.toString(),
        actionId = actionId,
        startedDate = startDate,
        endDate = endDate
    )
}