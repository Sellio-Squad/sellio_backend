package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshToken, UUID> {
    fun findByRefreshToken(refreshToken: String): RefreshToken?
    fun deleteAllByUserId(userId: UUID)
}
