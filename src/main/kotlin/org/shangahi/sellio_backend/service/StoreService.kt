package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.StoreDetailsResponse
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toStoreDetailsResponse
import org.shangahi.sellio_backend.entity.Store
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.repository.StoreRatingRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StoreService(
    private val storeRatingRepository: StoreRatingRepository,
    private val productRepository: ProductRepository,
    private val storeRepository: StoreRepository,
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
        val pageable = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        return storeRatingRepository.findTopStoresByHighestRating(pageable)
    }
}