package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "favorite")
data class Favorite(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(name = "product_id", nullable = false)
    val productId: UUID,
    @Column(name = "user_id", nullable = false)
    val userId: UUID,
)