package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "store")
data class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(name = "owner_id", nullable = false, unique = true)
    val ownerId: UUID,
    @Column(name = "title", unique = true, nullable = false)
    val title: String,
    @Column(name = "description", nullable = false)
    val description: String,
    @Column(name = "phone_number", unique = true)
    val phoneNumber: String? = null,
    @Column(name = "avatar_image_url", nullable = true)
    val avatarImageURL: String? = null,
    @Column(name = "cover_image_url", nullable = true)
    val coverImageURL: String? = null,
    @Column(name = "city", nullable = false)
    val city: String,
    @Column(name = "government", nullable = false)
    val government: String,
    @Column(name = "country", nullable = false)
    val country: String,
)