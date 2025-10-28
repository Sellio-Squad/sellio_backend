package org.shangahi.sellio_backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Table(name = "product_item", schema = "sellio_db")
@Entity
data class ProductItem(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    @Column(name = "product_id", nullable = false)
    val productId: UUID,
    @Column(name = "price")
    val price: Double,
    @Column(name ="discount_id")
    val discountId: UUID,
    @Column(name ="color_id")
    val colorId: UUID? = null,
    @Column(name ="size_id")
    val sizeId: UUID? = null,
    @Column(name ="weight_id")
    val weightId: UUID? = null,
    @Column(name ="stock")
    val stock: Int
)
