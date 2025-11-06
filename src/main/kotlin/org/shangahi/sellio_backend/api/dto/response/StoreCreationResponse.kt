package org.shangahi.sellio_backend.api.dto.response

import java.time.Instant
import java.util.*

data class StoreCreationResponse(
    val id: UUID,
    val title: String,
    val ownerId: UUID,
    val createdAt: Instant
)
