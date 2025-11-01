package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.FavoriteStore
import org.shangahi.sellio_backend.repository.FavoriteStoreRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class FavoriteStoreService(private val favoriteStoreRepository: FavoriteStoreRepository) {

    fun getFavoriteStoresByUserId(userId: UUID,pageable: Pageable): Page<FavoriteStore> {
        return favoriteStoreRepository.findByUserId(userId, pageable)
    }
}