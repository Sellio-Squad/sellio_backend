package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "sizes")
data class Size(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "value", nullable = false)
    val value: String
)


