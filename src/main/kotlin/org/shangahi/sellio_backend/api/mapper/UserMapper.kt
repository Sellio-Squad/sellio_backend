package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.UserInfoResponse
import org.shangahi.sellio_backend.entity.User

fun User.toResponse(): UserInfoResponse {
    return UserInfoResponse(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        country = country,
        avatarUrl = avatarUrl
    )
}