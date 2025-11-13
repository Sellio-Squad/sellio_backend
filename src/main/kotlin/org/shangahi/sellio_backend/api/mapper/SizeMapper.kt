package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.SizeRequest
import org.shangahi.sellio_backend.api.dto.response.SizeResponse
import org.shangahi.sellio_backend.entity.Size

fun SizeRequest.toEntity(): Size {
    return Size(
        value = value,
    )
}

fun Size.toResponse(): SizeResponse {
    return SizeResponse(
        id = id!!,
        value = value,
    )
}