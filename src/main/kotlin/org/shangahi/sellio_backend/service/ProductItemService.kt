package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.model.TrendingProduct
import org.shangahi.sellio_backend.repository.ProductItemRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductItemService(
    private val productItemRepository: ProductItemRepository
) {
    fun getTrendingProducts(pageable: Pageable): Page<TrendingProduct> {
        return productItemRepository.findTrendingProducts(OrderStatus.COMPLETED, pageable)
    }
}