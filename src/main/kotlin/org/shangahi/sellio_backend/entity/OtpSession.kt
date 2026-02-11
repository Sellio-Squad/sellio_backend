package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "otp_session")
data class OtpSession(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "session_id", nullable = false)
    val sessionId: UUID? = null,

    @Column(name = "phone_number", nullable = false, unique = true)
    val phoneNumber: String,

    @Column(name = "verified_at")
    var verifiedAt: Instant? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(name = "expires_at", nullable = false)
    val expiresAt: Instant,

    @Version
    @Column(name = "version")
    var version: Long? = null
)
