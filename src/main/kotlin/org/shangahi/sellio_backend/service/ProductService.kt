package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.service.exception.ProductNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    @Transactional(readOnly = true)
    fun getStoreProducts(storeId: UUID, page: Int, size: Int): Page<ProductCardResponse> {

        val sorting = Sort.by(Sort.Direction.DESC, "isFeatured")
        val pageable = PageRequest.of(page, size, sorting)
        val productPage = productRepository.findAllByStoreId(storeId, pageable)

        return productPage.map { product -> product.toProductCardResponse() }
    }

    fun searchProductsByTitle(title: String, pageable: Pageable): PageResponse<ProductCardResponse> {

        if (title.isBlank()) {
            throw ProductNotFoundException()
        }

        val productPage = productRepository.findByTitleContainingIgnoreCase(title, pageable)

        return productPage.toPageResponse{it.toProductCardResponse()}
    }
}