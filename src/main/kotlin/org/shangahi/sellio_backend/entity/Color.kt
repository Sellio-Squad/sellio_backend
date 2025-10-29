package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.util.*

@Table(name = "color")
@Entity
data class Color(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "value", nullable = false)
    val value: String
)
