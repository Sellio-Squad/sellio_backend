package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.CreateUserRequest
import org.shangahi.sellio_backend.api.dto.response.UserInfoResponse
import org.shangahi.sellio_backend.entity.User

fun User.toResponse(cdnEndpoint: String): UserInfoResponse {
    return UserInfoResponse(
        fullName = fullName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        country = country,
        avatarUrl = avatarUrl
    )
}


fun User.toResponse(): UserInfoResponse {
    return UserInfoResponse(
        fullName = fullName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        country = country,
        avatarUrl = avatarUrl
    )
}


