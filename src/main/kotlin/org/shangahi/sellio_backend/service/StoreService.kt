package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.CreateStoreRequest
import org.shangahi.sellio_backend.api.dto.response.StoreCreationResponse
import org.shangahi.sellio_backend.api.dto.response.StoreDetailsResponse
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toStoreDetailsResponse
import org.shangahi.sellio_backend.entity.Store
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.repository.StoreRatingRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class StoreService(
    private val storeRatingRepository: StoreRatingRepository,
    private val productRepository: ProductRepository,
    private val storeRepository: StoreRepository,
    private val userRepository: UserRepository,
) {

    @Transactional(readOnly = true)
    fun getStoreDetailsById(storeId: UUID): StoreDetailsResponse {

        val store = storeRepository.findById(storeId)
            .orElseThrow { Exception("Store not found with id: $storeId") }
        val featuredPageable = PageRequest.of(0, 10)
        val featuredProductsPage = productRepository.findStoreFeaturedProductsByStoreId(storeId, featuredPageable)
        val featuredProducts = featuredProductsPage.content.map { product -> product.toProductCardResponse() }

        return store.toStoreDetailsResponse(featuredProducts)
    }

    fun getPagedTopStores(pageable: Pageable): Page<Store> {
        return storeRatingRepository.findTopStoresByHighestRating(pageable)
    }

    @Transactional
    fun createStore(request: CreateStoreRequest): StoreCreationResponse {

        val ownerUser = userRepository.findByIdOrNull(request.ownerId) ?: throw UserNotFoundException()

        storeCreationValidation(request)

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
            createdAt = savedStore.createdAt ?: Instant.now(),
        )
    }

    private fun storeCreationValidation(request: CreateStoreRequest) {

        if (storeRepository.isExistByOwnerId(request.ownerId)) {
            throw StoreNotOwnerException()
        }

        if (storeRepository.existsByTitle(request.title)) {
            throw StoreTitleAlreadyExistException()
        }

        if (request.phoneNumber != null && storeRepository.existsByPhoneNumber(request.phoneNumber)) {
            throw StorePhoneNumberExistException()
        }
    }
}