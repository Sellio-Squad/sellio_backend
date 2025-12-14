package org.shangahi.sellio_backend.security.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.shangahi.sellio_backend.entity.User
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

    fun generateUserToken(
        user: User,
    ): String {
        return Jwts.builder()
            .setSubject(user.id.toString())
            .setIssuedAt(Date())
            .setExpiration(Date.from(Instant.now().plus(accessTokenExpiration)))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun parseClaims(token: String): UUID {
        val subject = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject

        return UUID.fromString(subject)
    }
}