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
    private val orderItemRepository: OrderItemRepository,
    private val favoriteProductRepository: FavoriteProductRepository,
    private val favoriteStoreRepository: FavoriteStoreRepository,
    private val storageService: StorageService,
    private val discountRepository: DiscountRepository
) {

    @Transactional(readOnly = true)
    fun getStoreDetailsById(storeId: UUID): StoreInfoResponse {

        val store = storeRepository.findByIdOrNull(storeId)
            ?: throw StoreNotFoundException()

        val featuredPageable = PageRequest.of(0, 10)
        val featuredProductsPage =
            productRepository.findStoreFeaturedProductsByStoreId(storeId, featuredPageable)

        val currentUserId = SecurityUtils.getCurrentUserId()
        val featuredProductIds = featuredProductsPage.content.map { it.id!! }
        val favoriteProductIds = if (currentUserId != null && featuredProductIds.isNotEmpty()) {
            favoriteProductRepository.findFavoriteProductIdsByUserIdAndProductIds(currentUserId, featuredProductIds)
        } else {
            emptySet()
        }
        val featuredProducts =
            featuredProductsPage.content.map { it.toProductCardResponse(favoriteProductIds.contains(it.id)) }

        val ratingStats = storeRatingRepository.getRatingStats(storeId)

        val discounts =
            discountRepository.findActiveStoreDiscounts(storeId)
                .map { it.toStoreDiscountResponse() }

        return store.toStoreDetailsResponse(
            featuredProducts,
            ratingStats.averageRating,
            discounts
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
        pageable: Pageable
    ): Page<StoreCardResponse> {

        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) return Page.empty(pageable)

        val storePage =
            if (city.isNullOrBlank())
                storeRepository.findStoresByTitleContainingIgnoreCase(pageable, trimmedTitle)
            else
                storeRepository.findStoresByTitleContainingIgnoreCaseAndCityIgnoreCase(trimmedTitle, city, pageable)

        return mapStoresToStoreCardPage(storePage)
    }

    @Transactional
    fun deleteStore(storeId: UUID): String {

        val store = storeRepository.findByIdOrNull(storeId)
            ?: throw StoreNotFoundException()
        val products = productRepository.findAllByStoreId(storeId)
        products.forEach { product ->
            val inUse = product.items.any { item ->
                orderItemRepository.existsByProductItemId(item.id!!)
            }
            if (inUse) throw ProductItemInUseException()
        }
        store.avatarImageURL?.let { storageService.deleteImage(it) }
        store.coverImageURL?.let { storageService.deleteImage(it) }
        products.forEach { product ->

            product.mainImageURL?.let { storageService.deleteImage(it) }

            product.items.forEach { item ->
                item.variationImageUrl?.let { storageService.deleteImage(it) }
            }

            product.images.forEach { img ->
                storageService.deleteImage(img.imageUrl)
            }

            discountRepository.deleteByProductId(product.id!!)
            favoriteProductRepository.deleteByProductId(product.id!!)
        }
        discountRepository.deleteByStoreId(storeId)
        favoriteStoreRepository.deleteByStoreId(storeId)
        productRepository.deleteAll(products)
        storeRepository.delete(store)
        return "Store deleted successfully"
    }

    @Transactional
    fun createStore(
        ownerId: UUID,
        request: CreateStoreRequest
    ): StoreCreationResponse {
        if (storeRepository.existsByTitle(request.title))
            throw StoreTitleAlreadyExistException()

        val owner = userRepository.findByIdOrNull(ownerId)
            ?: throw UserNotFoundException()

        storeCreationValidation(ownerId, request)
        if (storeRepository.existsByTitle(request.title)) {
            throw StoreTitleAlreadyExistException()
        }

        val ownerUser = userRepository.findByIdOrNull(ownerId) ?: throw UserNotFoundException()
        storeCreationValidation(ownerId,request)
        val newStore = Store(
            owner = owner,
            title = request.title,
            description = request.description,
            phoneNumber = request.phoneNumber,
            city = request.city,
            government = request.government,
            country = request.country,
        )

        val savedStore = storeRepository.save(newStore)
        return StoreCreationResponse(
            id = savedStore.id!!,
            title = savedStore.title,
            ownerId = savedStore.owner.id!!,
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

        val store = storeRepository.findByIdOrNull(storeId)
            ?: throw StoreNotFoundException()

        var updated = store

        if (newAvatar != null && !newAvatar.isEmpty) {
            store.avatarImageURL?.let { storageService.deleteImage(it) }
            val avatarUrl = storageService.uploadImage(newAvatar, store.title, "stores/avatars")
            updated = updated.copy(avatarImageURL = avatarUrl)
        }

        if (newCover != null && !newCover.isEmpty) {
            store.coverImageURL?.let { storageService.deleteImage(it) }
            val coverUrl = storageService.uploadImage(newCover, store.title, "stores/covers")
            updated = updated.copy(coverImageURL = coverUrl)
        }

        return storeRepository.save(updated)
    }

    private fun storeCreationValidation(ownerId: UUID, request: CreateStoreRequest) {

        if (storeRepository.isExistByOwnerId(ownerId))
            throw StoreNotOwnerException()

        if (storeRepository.existsByTitle(request.title))
            throw StoreTitleAlreadyExistException()

        if (request.phoneNumber != null &&
            storeRepository.existsByPhoneNumber(request.phoneNumber)
        ) {
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