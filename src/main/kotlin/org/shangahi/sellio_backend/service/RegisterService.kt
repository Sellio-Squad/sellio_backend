package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.mapper.toUser
import org.shangahi.sellio_backend.model.RegisterUserModel
import org.shangahi.sellio_backend.security.service.JwtService
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val refreshTokenService: RefreshTokenService
) {

    fun register(registerUserModel: RegisterUserModel): AuthResponse {
        if (userService.findUserByPhoneNumber(registerUserModel.phoneNumber) != null) {
            throw UserPhoneNumberAlreadyExistsException()
        }

        val encodedUser = registerUserModel.copy(
            password = passwordEncoder.encode(registerUserModel.password)
        )
        val saved = userService.insertUser(encodedUser.toUser())
        val token = jwtService.generateUserToken(saved)

        return AuthResponse(
            token,
            refreshTokenService.createRefreshToken(saved).refreshToken
        )
    }
}
