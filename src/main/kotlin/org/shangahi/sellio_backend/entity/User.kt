package org.shangahi.sellio_backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Table(name = "users", schema = "sellio_db")
@Entity
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String,
    @Column(name = "email",nullable = true)
    val email: String? = null,
    @Column(name = "password", nullable = false, unique = true)
    val password: String,
    @Column(name = "phone_number", nullable = false)
    val phoneNumber: String,
    @Column(name = "city", nullable = false)
    val city: String,
    @Column(name = "country", nullable = false)
    val country: String,
    @Column(name = "avatar_url", nullable = true)
    val avatarUrl: String? = null,
    @Column(name = "created_at")
    val createdAt: Instant = Instant.now()
)
