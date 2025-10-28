package org.shangahi.sellio_backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Table(name = "color")
@Entity
data class Color(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null ,
    @Column(name = "value", nullable = false)
    val value: String
)
