package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.security.service.JwtService
import org.shangahi.sellio_backend.service.exception.InvalidCredentialsException
import org.shangahi.sellio_backend.service.exception.InvalidRefreshTokenException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthenticationService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val refreshTokenService: RefreshTokenService
) {

    fun login(phoneNumber: String, password: String): AuthResponse {
        val user = userService.findUserByPhoneNumber(phoneNumber)
            ?: throw InvalidCredentialsException()

        if (!passwordEncoder.matches(password, user.password)) {
            throw InvalidCredentialsException()
        }

        val accessToken = jwtService.generateUserToken(user)
        val refreshAccessToken = refreshTokenService.createRefreshToken(user)
        return AuthResponse(accessToken,refreshAccessToken.refreshToken)
    }

    fun refreshToken(refreshToken: String): AuthResponse {
        val validToken = refreshTokenService.validateRefreshToken(refreshToken)

        refreshTokenService.deleteUserRefreshTokens(validToken?.user?.id ?: throw UserNotFoundException())

        val user = userService.findById(validToken.user.id)
        val newAccessToken = jwtService.generateUserToken(user)
        val newRefreshToken = refreshTokenService.createRefreshToken(user)

        return AuthResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken.refreshToken
        )
    }

    fun logout(userId: UUID) {
        refreshTokenService.deleteUserRefreshTokens(userId)
    }
}
