package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.ProductCardResponse
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

        return productPage.map { product ->

            ProductCardResponse(
                id = product.id ?: throw IllegalStateException("Product ID was null for product ${product.title}"),
                title = product.title,
                price = product.price,
                mainImageUrl = product.mainImageURL,
            )
        }
    }
}