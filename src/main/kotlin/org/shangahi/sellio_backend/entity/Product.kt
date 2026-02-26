package org.shangahi.sellio_backend.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "product_type")
@DiscriminatorValue("NEW")
@Entity

open class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open val id: UUID? = null,

    @Column(name = "title", nullable = false)
    open var title: String,

    @Column(name = "description", nullable = true)
    open var description: String?,

    @Column(name = "main_image_url", nullable = true)
    open var mainImageURL: String?,

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    open var items: Set<ProductItem> = emptySet(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    open val store: Store,

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    open val productSubCategories: Set<ProductSubCategory> = emptySet(),

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    open val images: Set<ProductImage> = emptySet(),

    @Column(name = "is_used", nullable = false)
    open var isUsed: Boolean = false,

    @Column(name = "is_featured", nullable = false)
    open var isFeatured: Boolean = false,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open var createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    open var updatedAt: Instant? = null,
) {
    fun getMinPrice() = items.minOfOrNull { it.price }


    fun  getMaxDiscount() = items.filter { it.discount?.type == Discount.DiscountType.PERCENTAGE }
        .maxOfOrNull { it.discount?.value ?: 0.0 }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Product
        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return "Product(id=$id, title='$title', price=${getMinPrice()})"
    }
}
