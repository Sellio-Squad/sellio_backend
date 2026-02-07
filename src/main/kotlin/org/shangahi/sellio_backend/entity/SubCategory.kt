package org.shangahi.sellio_backend.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "sub_category")
data class SubCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    @JsonBackReference
    val category: Category,

    @Column(name = "title", nullable = false, unique = true)
    val title: String = "",

    @Column(name = "image_url")
    val imageUrl: String? = null,

    @OneToMany(mappedBy = "subCategory", fetch = FetchType.LAZY)
    @JsonManagedReference
    val products: MutableSet<ProductSubCategory> = mutableSetOf(),  // FIXED

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant? = null
)
