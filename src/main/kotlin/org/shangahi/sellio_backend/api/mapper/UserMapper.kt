package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.CreateUserRequest
import org.shangahi.sellio_backend.api.dto.response.UserInfoResponse
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.model.RegisterUserModel

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

fun RegisterUserModel.toUser(): User {
    return User(
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        email = null,
        city = city,
        country = country,
        password = password,
        avatarUrl = null
    )
}


