package org.shangahi.sellio_backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.shangahi.sellio_backend.model.ContactType
import java.util.UUID

@Entity
@Table(name = "store_contacts")
data class StoreContact(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    val store: Store,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: ContactType,

    @Column(name = "value", nullable = false)
    val value: String,
)
