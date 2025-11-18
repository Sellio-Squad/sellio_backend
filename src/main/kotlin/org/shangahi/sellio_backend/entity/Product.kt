package org.shangahi.sellio_backend.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Table(name = "product")
@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "description", nullable = true)
    val description: String?,

    @Column(name = "main_image_url", nullable = true)
    val mainImageURL: String?,

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    val items: Set<ProductItem> = emptySet(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonManagedReference
    val productSubCategories: Set<ProductSubCategory> = emptySet(),

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    val images: Set<ProductImage> = emptySet(),

    @Column(name = "price", nullable = false)
    val price: Double,

    @Column(name = "is_used", nullable = false)
    val isUsed: Boolean = false,

    @Column(name = "is_featured", nullable = false)
    val isFeatured: Boolean = false,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    val updatedAt: Instant? = null,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Product
        return id != null && id == other.id
    }
    override fun hashCode(): Int = javaClass.hashCode()
    @Override
    override fun toString(): String {
        return "Product(id=$id, title='$title', price=$price)"
    }
}
