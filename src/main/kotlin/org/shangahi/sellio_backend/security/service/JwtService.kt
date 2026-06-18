package org.shangahi.sellio_backend.security.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.model.Role
import org.shangahi.sellio_backend.service.exception.MissingActiveRoleException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.Base64
import java.util.Date
import java.util.UUID

@Service
class JwtService(
    @param:Value("\${jwt.secret}") private val jwtSecret: String,
) {
    private val accessTokenExpiration = Duration.ofDays(30)

    private val secretKey = Keys.hmacShaKeyFor(
        Base64.getDecoder().decode(jwtSecret)
    )

    fun generateUserToken(user: User, activeRole: Role): String {
        return Jwts.builder()
            .setSubject(user.id.toString())
            .claim(ACTIVE_ROLE_CLAIM, activeRole.name)
            .setIssuedAt(Date())
            .setExpiration(Date.from(Instant.now().plus(accessTokenExpiration)))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun parseSubject(token: String): UUID {
        return UUID.fromString(parseClaims(token).subject)
    }

    fun getActiveRole(token: String): Role {
        val roleName = parseClaims(token)[ACTIVE_ROLE_CLAIM] as? String
            ?: throw MissingActiveRoleException()

        return Role.valueOf(roleName)
    }

    private fun parseClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    companion object {
        const val ACTIVE_ROLE_CLAIM = "activeRole"
    }
}