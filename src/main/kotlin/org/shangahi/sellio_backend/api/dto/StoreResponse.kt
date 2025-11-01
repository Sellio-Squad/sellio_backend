package org.shangahi.sellio_backend.api.dto

import java.util.UUID

data class StoreResponse (
    val id: UUID?,
    val title: String,
    val city: String,
    val government: String,
    val country: String,
    val avatarImageURL: String?,
    val coverImageURL: String?
)