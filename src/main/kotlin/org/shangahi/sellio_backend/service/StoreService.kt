package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.CreateStoreRequest
import org.shangahi.sellio_backend.api.dto.request.StoreCardResponse
import org.shangahi.sellio_backend.api.dto.response.StoreCreationResponse
import org.shangahi.sellio_backend.api.dto.response.StoreInfoResponse
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toStoreCardResponse
import org.shangahi.sellio_backend.api.mapper.toStoreDetailsResponse
import org.shangahi.sellio_backend.api.mapper.toStoreDiscountResponse
import org.shangahi.sellio_backend.entity.Store
import org.shangahi.sellio_backend.repository.*
import org.shangahi.sellio_backend.service.exception.*
import org.shangahi.sellio_backend.security.SecurityUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import java.util.*

@Service
class StoreService(
    private val storeRatingRepository: StoreRatingRepository,
    private val productRepository: ProductRepository,
    private val storeRepository: StoreRepository,
    private val userRepository: UserRepository,
    private val storageService: StorageService,
    private val favoriteStoreRepository: FavoriteStoreRepository,
    private val discountRepository: DiscountRepository,
    private val favoriteProductRepository: FavoriteProductRepository
) {

    @Transactional(readOnly = true)
    fun getStoreDetailsById(storeId: UUID): StoreInfoResponse {

        val store = storeRepository.findByIdOrNull(storeId)
            ?: throw StoreNotFoundException()
        val featuredPageable = PageRequest.of(0, 10)
        val featuredProductsPage = productRepository.findStoreFeaturedProductsByStoreId(storeId, featuredPageable)
        val currentUserId = SecurityUtils.getCurrentUserId()
        val featuredProductIds = featuredProductsPage.content.map { it.id!! }
        val favoriteProductIds = if (currentUserId != null && featuredProductIds.isNotEmpty()) {
            favoriteProductRepository.findFavoriteProductIdsByUserIdAndProductIds(currentUserId, featuredProductIds)
        } else {
            emptySet()
        }
        val featuredProducts = featuredProductsPage.content.map { product -> product.toProductCardResponse(favoriteProductIds.contains(product.id)) }
        val storeRating = storeRatingRepository.getRatingStats(storeId)
        val activeStoreDiscounts =
            discountRepository
                .findActiveStoreDiscounts(storeId)
                .map { it.toStoreDiscountResponse() }
        return store.toStoreDetailsResponse(
            featuredProducts,
            storeRating.averageRating,
            activeStoreDiscounts
        )
    }


    @Transactional(readOnly = true)
    fun getPagedTopStores(pageable: Pageable): Page<StoreCardResponse> {

        val storesPage = storeRatingRepository.findTopStoresByHighestRating(pageable)
        return mapStoresToStoreCardPage(storesPage)
    }

    fun searchStoresByTitle(
        title: String,
        city: String?,
        pageable: Pageable): Page<StoreCardResponse> {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) {
            return Page.empty(pageable)
        }
        val storePage = if (city.isNullOrBlank()) {
            storeRepository.findStoresByTitleContainingIgnoreCase(pageable, trimmedTitle)
        } else {
            storeRepository.findStoresByTitleContainingIgnoreCaseAndCityIgnoreCase(trimmedTitle, city, pageable)
        }
        return mapStoresToStoreCardPage(storePage)
    }

    @Transactional
    fun createStore(
        ownerId: UUID,
        request: CreateStoreRequest
    ): StoreCreationResponse {
        if (storeRepository.existsByTitle(request.title)) {
            throw StoreTitleAlreadyExistException()
        }

        val ownerUser = userRepository.findByIdOrNull(ownerId) ?: throw UserNotFoundException()
        storeCreationValidation(ownerId,request)
        val newStore = Store(
            owner = ownerUser,
            title = request.title,
            description = request.description,
            phoneNumber = request.phoneNumber,
            city = request.city,
            government = request.government,
            country = request.country,
        )

        val savedStore = storeRepository.save(newStore)
        return StoreCreationResponse(
            id = savedStore.id ?: throw StoreNotFoundException(),
            title = savedStore.title,
            ownerId = savedStore.owner.id ?: throw UserNotFoundException(),
            avatarUrl = savedStore.avatarImageURL.orEmpty(),
            coverUrl = savedStore.coverImageURL.orEmpty(),
            createdAt = savedStore.createdAt ?: Instant.now()
        )
    }

    @Transactional
    fun uploadStoreImages(
        storeId: UUID,
        newAvatar: MultipartFile?,
        newCover: MultipartFile?
    ): Store {
        val store = storeRepository.findByIdOrNull(storeId) ?: throw StoreNotFoundException()

        var updatedStore = store

        if (newAvatar != null && !newAvatar.isEmpty) {
            store.avatarImageURL?.let { storageService.deleteImage(it) }
            val avatarUrl = storageService.uploadImage(newAvatar, store.title, "stores/avatars")
            updatedStore = updatedStore.copy(avatarImageURL = avatarUrl)
        }

        if (newCover != null && !newCover.isEmpty) {
            store.coverImageURL?.let { storageService.deleteImage(it) }
            val coverUrl = storageService.uploadImage(newCover, store.title, "stores/covers")
            updatedStore = updatedStore.copy(coverImageURL = coverUrl)
        }

        return storeRepository.save(updatedStore)
    }

    private fun storeCreationValidation(
        ownerId: UUID,
        request: CreateStoreRequest
    ) {

        if (storeRepository.isExistByOwnerId(ownerId)) {
            throw StoreNotOwnerException()
        }

        if (storeRepository.existsByTitle(request.title)) {
            throw StoreTitleAlreadyExistException()
        }

        if (request.phoneNumber != null && storeRepository.existsByPhoneNumber(request.phoneNumber)) {
            throw StorePhoneNumberExistException()
        }
    }


    private fun mapStoresToStoreCardPage(storesPage: Page<Store>): Page<StoreCardResponse> {

        if (storesPage.isEmpty) {
            return Page.empty(storesPage.pageable)
        }

        val storeIds = storesPage.content.map { it.id!! }
        val currentUserId = SecurityUtils.getCurrentUserId()
        val discountsStats = discountRepository.findMaxDiscountByStoreIds(storeIds)
        val discountsMap = discountsStats.associate { it.storeId to it.maxDiscount }
        val favoriteStoreIds = if (currentUserId != null) {
            favoriteStoreRepository.findFavoriteStoreIdsByUserIdAndStoreIds(currentUserId, storeIds)
        } else {
            emptySet()
        }
        return storesPage.map { store -> store.toStoreCardResponse(discountsMap, favoriteStoreIds.contains(store.id)) }
    }

}