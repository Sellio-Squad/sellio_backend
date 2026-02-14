package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.StoreCardResponse
import org.shangahi.sellio_backend.entity.FavoriteStore
import org.shangahi.sellio_backend.repository.DiscountRepository
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
    private val storeRepository: StoreRepository,
    private val discountRepository: DiscountRepository,
) {

    @Transactional
    fun toggleFavoriteStore(
        userId: UUID,
        storeId: UUID
    ): FavoriteStore {
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException() }
        val store = storeRepository.findById(storeId)
            .orElseThrow { StoreNotFoundException() }

        val existingStoreFavorite = favoriteStoreRepository.findFavoriteStoresByUserIdAndStoreId(userId, storeId)

        return if (existingStoreFavorite != null) {

            favoriteStoreRepository.deleteFavoriteStoreByUserIdAndStoreId(userId, storeId)
            existingStoreFavorite
        } else {
            favoriteStoreRepository.save(FavoriteStore(user = user, store = store))
        }
    }

    @Transactional(readOnly = true)
    fun getFavoriteStoresByUserId(userId: UUID, pageable: Pageable): Page<StoreCardResponse> {
        if (!userRepository.existsById(userId)) {
            throw UserNotFoundException()
        }
        val favoritesPage = favoriteStoreRepository.findByUserId(userId, pageable)

        if (favoritesPage.isEmpty) {
            return Page.empty(pageable)
        }
        val storeIds = favoritesPage.content.map { it.store.id!! }
        val discountsStats = discountRepository.findMaxDiscountByStoreIds(storeIds)
        val discountsMap = discountsStats.associate { it.storeId to it.maxDiscount }

        return favoritesPage.map { favStore ->
            val store = favStore.store

            StoreCardResponse(
                id = store.id,
                title = store.title,
                coverImageURL = store.coverImageURL,
                maxDiscount = discountsMap[store.id],
                isFavorite = true
            )
        }
    }
}