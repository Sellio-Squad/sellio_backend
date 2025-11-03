package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.DiscountResponse
import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toDiscountResponse
import org.shangahi.sellio_backend.repository.DiscountRepository
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.shangahi.sellio_backend.service.exception.ProductNotFoundException
import org.shangahi.sellio_backend.service.exception.StoreNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DiscountService(
    private val discountRepository: DiscountRepository,
    private val productRepository: ProductRepository,
    private val storeRepository: StoreRepository
) {

    fun getDiscountsByStoreId(storeId: UUID, pageable: Pageable): PageResponse<DiscountResponse> {

        if (!storeRepository.existsById(storeId)) {
            throw StoreNotFoundException()
        }

        val discountPage = discountRepository.findByStoreId(storeId, pageable)

        return discountPage.toPageResponse { it.toDiscountResponse() }
    }

    fun getDiscountsByProductId(productId: UUID, pageable: Pageable): PageResponse<DiscountResponse> {

        if (!productRepository.existsById(productId)) {
            throw ProductNotFoundException()
        }

        val discountPage = discountRepository.findByProductId(productId, pageable)

        return discountPage.toPageResponse { it.toDiscountResponse() }
    }

}