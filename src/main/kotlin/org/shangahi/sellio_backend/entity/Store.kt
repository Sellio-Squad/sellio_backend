package org.shangahi.sellio_backend.entity

import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "store")
data class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    val owner: User,

    @Column(name = "title", unique = true, nullable = false)
    val title: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "phone_number", unique = true)
    val phoneNumber: String? = null,

    @Column(name = "avatar_image_url", nullable = true)
    val avatarImageURL: String? = null,

    @Column(name = "cover_image_url", nullable = true)
    val coverImageURL: String? = null,

    @Column(name = "city", nullable = false)
    val city: String,

    @Column(name = "government", nullable = false)
    val government: String,

    @Column(name = "country", nullable = false)
    val country: String,

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    val favoriteByUsers: Set<FavoriteStore> = emptySet(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Store
        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String = "Store(id=$id, title='$title')"

}