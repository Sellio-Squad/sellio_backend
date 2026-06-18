package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import org.shangahi.sellio_backend.model.Role
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "refresh_token", nullable = false, unique = true)
    val refreshToken: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "active_role", nullable = false)
    val activeRole: Role,

    @Column(nullable = false)
    val expiryDate: Instant
)
