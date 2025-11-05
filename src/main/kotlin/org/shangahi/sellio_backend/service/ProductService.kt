package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.entity.Product
import org.shangahi.sellio_backend.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    @Transactional(readOnly = true)
    fun getStoreProducts(storeId: UUID, pageable: Pageable): PageResponse<ProductCardResponse> {

        val productPage = productRepository.findAllByStoreId(storeId, pageable)

        return productPage.toPageResponse { it.toProductCardResponse()}
    }

    fun searchProductsByTitle(title: String, pageable: Pageable): PageResponse<ProductCardResponse> {

        val trimmedTitle = title.trim()

        if (trimmedTitle.isBlank()) {
            val emptyPage: Page<Product> = Page.empty(pageable)
            return emptyPage.toPageResponse{it.toProductCardResponse()}
        }


        val productPage = productRepository.findByTitleContainingIgnoreCase(title, pageable)

        return productPage.toPageResponse{it.toProductCardResponse()}
    }
}