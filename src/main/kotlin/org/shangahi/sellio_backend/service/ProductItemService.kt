package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.ProductItemRequest
import org.shangahi.sellio_backend.entity.ProductItem
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.model.TrendingProduct
import org.shangahi.sellio_backend.repository.*
import org.shangahi.sellio_backend.service.exception.ProductNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductItemService(
    private val productItemRepository: ProductItemRepository,
    private val productRepository: ProductRepository,
    private val colorRepository: ColorRepository,
    private val sizeRepository: SizeRepository,
    private val weightRepository: WeightRepository,
    private val discountRepository: DiscountRepository
) {
    fun getTrendingProducts(pageable: Pageable): Page<TrendingProduct> {
        val trendingProducts = productItemRepository.findTrendingProducts(OrderStatus.COMPLETED, pageable)

        return if (trendingProducts.isEmpty) {
            productItemRepository.findAllProducts(pageable)
        } else {
            trendingProducts
        }
    }
    fun insertProductItem(request: ProductItemRequest,productId: UUID): ProductItem {

        val product = productRepository.findByIdOrNull(productId)
            ?: throw ProductNotFoundException()

        val color = request.colorId?.let {
            colorRepository.findByIdOrNull(it)
        }
        val size = request.sizeId?.let {
            sizeRepository.findByIdOrNull(it)
        }
        val weight = request.weightId?.let {
            weightRepository.findByIdOrNull(it)
        }

        val discount = request.discountId?.let {
            discountRepository.findByIdOrNull(it)
        }
        val productItem = ProductItem(
            product = product,
            price = request.price,
            discount = discount,
            color = color,
            size = size,
            weight = weight,
            stock = request.stock,
            variationImageUrl = request.variationImageUrl,
        )

        return productItemRepository.save(productItem)
    }
}