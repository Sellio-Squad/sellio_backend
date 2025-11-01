package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.shangahi.sellio_backend.model.OrderStatus
import java.time.Instant
import java.util.*

@Entity
@Table(name = "order_item")
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    val productItem: ProductItem,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: Orders,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: OrderStatus = OrderStatus.IN_PROGRESS,

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant? = null
)
