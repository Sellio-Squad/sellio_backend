package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "offer")
data class Offer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false)
    val imageUrl: String,
    var title: String? = null,
    @Column(nullable = false)
    var isActive: Boolean = true,
    var priority: Int = 0,
    @Enumerated(EnumType.STRING)
    val actionType: BannerActionType,
    val actionId: String? = null,
    val startDate: Instant? = null,
    val endDate: Instant? = null
)

enum class BannerActionType {
    PRODUCT,
    CATEGORY,
    STORE,
    NONE
}