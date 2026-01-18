package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "pending_registration")
data class PendingRegistration(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "session_id", nullable = false)
    val sessionId: UUID? = null,

    @Column(name = "full_name", nullable = false)
    val fullName: String,

    @Column(name = "phone_number", nullable = false, unique = true)
    val phoneNumber: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "email")
    val email: String?,

    @Column(name = "city", nullable = false)
    val city: String,

    @Column(name = "country", nullable = false)
    val country: String,

    @Column(name = "avatar_url")
    val avatarUrl: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now()
)
