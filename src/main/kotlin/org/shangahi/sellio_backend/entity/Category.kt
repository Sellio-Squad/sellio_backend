package org.shangahi.sellio_backend.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "category")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "title", nullable = false, unique = true)
    val title: String = "",

    @Column(name = "image_url")
    val imageUrl: String? = null,

    @OneToMany(
        mappedBy = "category",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonManagedReference
    val subCategories: MutableSet<SubCategory> = mutableSetOf(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    val updatedAt: Instant? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Category

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "Category(id=$id, title='$title', imageUrl=$imageUrl)"
    }
}