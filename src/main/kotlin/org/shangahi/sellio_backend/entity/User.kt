package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Table(name = "users")
@Entity
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Column(name = "email", nullable = true, unique = true)
    val email: String? = null,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "phone_number", nullable = false, unique = true)
    val phoneNumber: String,

    @Column(name = "city", nullable = false)
    val city: String,

    @Column(name = "country", nullable = false)
    val country: String,

    @Column(name = "avatar_url", nullable = true)
    val avatarUrl: String? = null,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val favoriteProducts: Set<FavoriteProduct> = emptySet(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val favoriteStores: Set<FavoriteStore> = emptySet(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    val updatedAt: Instant? = null
)
