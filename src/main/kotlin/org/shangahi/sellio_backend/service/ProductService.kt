package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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
}