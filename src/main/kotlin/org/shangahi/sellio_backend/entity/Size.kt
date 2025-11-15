package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "sizes")
data class Size(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "value", nullable = false)
    val value: String
)


