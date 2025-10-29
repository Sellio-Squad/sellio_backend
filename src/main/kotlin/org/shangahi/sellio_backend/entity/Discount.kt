package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "discounts")
data class Discount(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    val store: Store? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    val subCategory: SubCategory? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: DiscountType,

    @Column(name = "value", nullable = false)
    val value: Double,

    @Column(name = "start_date")
    val startDate: Instant? = null,

    @Column(name = "end_date")
    val endDate: Instant? = null
) {
    enum class DiscountType {
        FIXED,
        PERCENTAGE,
    }
}
