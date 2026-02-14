package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "pending_registration")
data class PendingRegistration(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    val id: UUID? = null,

    @Column(name = "full_name", nullable = false)
    var fullName: String,

    @Column(name = "phone_number", nullable = false, unique = true)
    val phoneNumber: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "email")
    var email: String?,

    @Column(name = "country_code", nullable = false)
    var countryCode: String,

    @Column(name = "city", nullable = false)
    var city: String,

    @Column(name = "country", nullable = false)
    var country: String,

    @Column(name = "avatar_url")
    var avatarUrl: String? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now()
)
