package org.shangahi.sellio_backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "weight")
data class Weight(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(name = "value", nullable = false)
    val value: Double,
)