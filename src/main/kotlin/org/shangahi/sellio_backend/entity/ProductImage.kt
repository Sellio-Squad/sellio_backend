package org.shangahi.sellio_backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Table(name = "product_image")
@Entity
data class ProductImage (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(name = "product_id", nullable = false)
    val productId: UUID,
    @Column(name = "image_url")
    val imageUrl: String
)