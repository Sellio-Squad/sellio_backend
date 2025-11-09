package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.FavoriteStore
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FavoriteStoreRepository : JpaRepository<FavoriteStore, UUID> {

    fun findByUserId(userId: UUID, pageable: Pageable): Page<FavoriteStore>
    fun deleteFavoriteStoreByUserIdAndStoreId(userId: UUID, storeId: UUID)
    fun findFavoriteStoresByUserIdAndStoreId(userId: UUID, storeId: UUID): FavoriteStore?

}
