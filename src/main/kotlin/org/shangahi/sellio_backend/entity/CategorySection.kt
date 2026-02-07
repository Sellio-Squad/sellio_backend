package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "category_section")
data class CategorySection(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @Column(name = "section_title", nullable = false)
    val sectionTitle: String,
    @Column(name = "category_id", nullable = false)
    val categoryId: UUID,
    @Column(name = "sort_order", nullable = false)
    val sortOrder: Int,
    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true
)