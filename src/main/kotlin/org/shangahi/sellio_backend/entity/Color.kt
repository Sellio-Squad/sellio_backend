package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.util.*

@Table(name = "color")
@Entity
data class Color(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "value", nullable = false)
    val value: String
)
