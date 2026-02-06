package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "otp_logs")
data class OtpLog(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,

    @Column(name = "otp", nullable = false, length = 4)
    val otp: String,

    @Column(name = "session_id", nullable = false)
    val sessionId: UUID = UUID.randomUUID(),

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean = false,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now()
)
