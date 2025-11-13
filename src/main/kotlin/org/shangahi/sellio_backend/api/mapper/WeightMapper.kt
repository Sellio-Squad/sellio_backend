package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.WeightRequest
import org.shangahi.sellio_backend.api.dto.response.WeightResponse
import org.shangahi.sellio_backend.entity.Weight

fun WeightRequest.toEntity(): Weight {
    return Weight(
        value = value,
    )
}

fun Weight.toResponse(): WeightResponse {
    return WeightResponse(
        id = id!!,
        value = value,
    )
}