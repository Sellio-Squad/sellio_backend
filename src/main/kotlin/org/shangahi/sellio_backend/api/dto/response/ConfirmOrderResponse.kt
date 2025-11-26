package org.shangahi.sellio_backend.api.dto.response

import java.util.UUID

data class ConfirmOrderResponse(
    val message: String,
    val orderIds: List<UUID>
)
