package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.UserInsertRequest
import org.shangahi.sellio_backend.api.dto.request.UserUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.UserInfoResponse
import org.shangahi.sellio_backend.entity.User

fun User.toResponse(cdnEndpoint: String): UserInfoResponse {
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

fun UserInsertRequest.toUser(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        email = email,
        city = city,
        country = country,
        password = password,
        avatarUrl = avatarUrl
    )
}

fun UserUpdateRequest.toUser(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        email = email,
        city = city,
        country = country,
        password = password,
        avatarUrl = avatarUrl
    )
}

fun User.toResponse(): UserInfoResponse {
    return UserInfoResponse(
        id = id!!,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        city = city,
        country = country,
        avatarUrl = avatarUrl
    )
}
