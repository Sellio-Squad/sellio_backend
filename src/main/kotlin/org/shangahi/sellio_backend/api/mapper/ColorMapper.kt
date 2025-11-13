package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.ColorRequest
import org.shangahi.sellio_backend.api.dto.response.ColorResponse
import org.shangahi.sellio_backend.entity.Color

fun ColorRequest.toEntity(): Color {
    return Color(
        value = value,
    )
}

fun Color.toResponse(): ColorResponse {
    return ColorResponse(
        id = id!!,
        value = value,
    )
}