package org.shangahi.sellio_backend.api.dto.response


data class UserInfoResponse(
    val fullName:String,
    val email: String?,
    val phoneNumber: String? = null,
    val city: String,
    val country: String,
    val avatarUrl: String? = null,
)
