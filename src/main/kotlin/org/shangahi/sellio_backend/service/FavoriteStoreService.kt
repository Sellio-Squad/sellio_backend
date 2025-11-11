package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.FavouriteStoreRequest
import org.shangahi.sellio_backend.entity.FavoriteStore
import org.shangahi.sellio_backend.repository.FavoriteStoreRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.StoreNotFoundException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class FavoriteStoreService(
    private val favoriteStoreRepository: FavoriteStoreRepository,
    private val userRepository: UserRepository,
    private val storeRepository: StoreRepository
) {
    @Transactional
    fun toggleFavoriteStore(request: FavouriteStoreRequest): FavoriteStore {
        val user = userRepository.findById(request.userId)
            .orElseThrow { UserNotFoundException() }
        val store = storeRepository.findById(request.storeId)
            .orElseThrow { StoreNotFoundException() }

        val existingStoreFavorite =
            favoriteStoreRepository.findFavoriteStoresByUserIdAndStoreId(request.userId, request.storeId)

        return if (existingStoreFavorite != null) {

            favoriteStoreRepository.deleteFavoriteStoreByUserIdAndStoreId(request.userId, request.storeId)
            existingStoreFavorite
        } else {
            favoriteStoreRepository.save(FavoriteStore(user = user, store = store))
        }
    }

    fun getFavoriteStoresByUserId(userId: UUID, pageable: Pageable): Page<FavoriteStore> {
        if (!userRepository.existsById(userId)) {
            throw UserNotFoundException()
        }
        return favoriteStoreRepository.findByUserId(userId, pageable)
    }
}