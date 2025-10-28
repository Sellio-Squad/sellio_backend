package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import java.util.*

@Table(name = "product", schema = "sellio_db")
@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    @Column(name = "title")
    val title: String,
    @Column(name = "description", nullable = true)
    val description: String?,
    @Column(name = "store_id")
    val storeId: UUID,
    @Column(name = "sub_category_id")
    val subCategoryId: UUID,
    @Column(name = "is_used")
    val isUsed: Boolean = false,
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant? = null
)
