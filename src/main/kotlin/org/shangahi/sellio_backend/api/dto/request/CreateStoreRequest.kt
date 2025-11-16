package org.shangahi.sellio_backend.api.dto.request

data class CreateStoreRequest(
    val title: String,
    val description: String,
    val phoneNumber: String?,
    val city: String,
    val government: String,
    val country: String,
)
