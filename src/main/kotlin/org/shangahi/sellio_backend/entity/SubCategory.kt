package org.shangahi.sellio_backend.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.Hibernate
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
    val products: MutableSet<ProductSubCategory> = mutableSetOf(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SubCategory

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "SubCategory(id=$id, title='$title', imageUrl=$imageUrl)"
    }
}