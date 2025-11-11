package org.shangahi.sellio_backend.api.dto.response

import java.util.UUID

data class UserInfoResponse(
    val id: UUID? = null,
    val firstName: String,
    val lastName: String,
    val email: String?,
    val phoneNumber: String? = null,
    val city: String,
    val country: String,
    val avatarUrl: String? = null,
)
