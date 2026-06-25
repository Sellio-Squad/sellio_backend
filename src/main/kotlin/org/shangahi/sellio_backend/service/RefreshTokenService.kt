package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.RefreshToken
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.model.Role
import org.shangahi.sellio_backend.repository.RefreshTokenRepository
import org.shangahi.sellio_backend.service.exception.InvalidRefreshTokenException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun createRefreshToken(user: User,activeRole: Role): RefreshToken {
        val token = UUID.randomUUID().toString()
        val expiryDate = Instant.now().plus(Duration.ofDays(60))

        val refreshToken = RefreshToken(
            refreshToken = token,
            user = user,
            activeRole = activeRole,
            expiryDate = expiryDate
        )

        return refreshTokenRepository.save(refreshToken)
    }

    fun validateRefreshToken(token: String): RefreshToken? {
        val stored = refreshTokenRepository.findByRefreshToken(token) ?:  throw InvalidRefreshTokenException()
        return if (stored.expiryDate.isAfter(Instant.now())) stored else null
    }

    @Transactional
    fun deleteUserRefreshTokens(userId: UUID) {
        refreshTokenRepository.deleteAllByUserId(userId)
    }
}
