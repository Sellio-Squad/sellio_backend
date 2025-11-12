package org.shangahi.sellio_backend.api.dto.request

data class RequestOtpRequest(
    val phoneNumber: String,
    val defaultRegion: String
)