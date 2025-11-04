package org.shangahi.sellio_backend.api.dto.request

import java.util.UUID

data class CreateStoreRequest(
    val ownerId: UUID,
    val title: String,
    val description: String,
    val phoneNumber: String?,
    val city: String,
    val government: String,
    val country: String,
)
