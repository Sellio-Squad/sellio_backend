package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.ProductItemRequest
import org.shangahi.sellio_backend.api.dto.request.ProductItemUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.TrendingProductResponse
import org.shangahi.sellio_backend.api.mapper.toPagedResponse
import org.shangahi.sellio_backend.entity.Product
import org.shangahi.sellio_backend.entity.ProductItem
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.repository.*
import org.shangahi.sellio_backend.service.exception.ProductItemInUseException
import org.shangahi.sellio_backend.service.exception.ProductItemNotFoundException
import org.shangahi.sellio_backend.service.exception.ProductNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductItemService(
    private val productItemRepository: ProductItemRepository,
    private val productRepository: ProductRepository,
    private val colorRepository: ColorRepository,
    private val sizeRepository: SizeRepository,
    private val discountRepository: DiscountRepository,
    private val cartItemRepository: CartItemRepository,
    private val orderItemRepository: OrderItemRepository,
    private val favoriteProductRepository: FavoriteProductRepository
) {
    fun getTrendingProducts(userId: UUID?, pageable: Pageable): PageResponse<TrendingProductResponse> {
        val trendingProducts =
            productItemRepository.findTrendingProducts(OrderStatus.COMPLETED, pageable)
                .takeIf { !it.isEmpty }
                ?: productItemRepository.findAllProducts(pageable)
        val productIds = trendingProducts.content.map { it.productId }

        val favoriteIds =
            if (userId != null && productIds.isNotEmpty()) {
                favoriteProductRepository
                    .findFavoriteProductIdsByUserIdAndProductIds(userId, productIds)
            } else emptySet()

        return trendingProducts.toPagedResponse(favoriteIds)
    }


    fun getProductItems(productId: UUID): List<ProductItem> {
        if (!productRepository.existsById(productId)) {
            throw ProductNotFoundException()
        }
        val productItems = productItemRepository.findAllByProductIdWithDetails(productId)

        return productItems
    }

    @Transactional
    fun insertProductItem(request: ProductItemRequest, productId: UUID): ProductItem {

        val product = productRepository.findByIdOrNull(productId)
            ?: throw ProductNotFoundException()

        removeBaseItemIfExists(product)

        val productItem = mapToProductItem(request, product)

        return productItemRepository.save(productItem)
    }

    @Transactional
    fun addProductItems(productId: UUID, requests: List<ProductItemRequest>): List<ProductItem> {

        val product = productRepository.findByIdOrNull(productId)
            ?: throw ProductNotFoundException()

        removeBaseItemIfExists(product)

        val newItems = requests.map { request ->
            mapToProductItem(request, product)
        }

        return productItemRepository.saveAll(newItems)
    }

    private fun mapToProductItem(request: ProductItemRequest, product: Product): ProductItem {
        val color = request.colorId?.let {
            colorRepository.findByIdOrNull(it)
        }
        val size = request.sizeId?.let {
            sizeRepository.findByIdOrNull(it)
        }

        val discount = request.discountId?.let {
            discountRepository.findByIdOrNull(it)
        }
        return ProductItem(
            product = product,
            price = request.price,
            discount = discount,
            color = color,
            size = size,
            stock = request.stock,
            variationImageUrl = request.variationImageUrl,
        )

    }

    @Transactional
    fun deleteProductItem(productId: UUID, itemId: UUID): String {

        val item = productItemRepository.findByIdOrNull(itemId)
            ?: throw ProductItemNotFoundException()

        if (item.product.id != productId) {
            throw ProductItemNotFoundException()
        }

        if (cartItemRepository.existsByProductItemId(itemId)) {
            throw ProductItemInUseException()
        }

        if (orderItemRepository.existsByProductItemId(itemId)) {
            throw ProductItemInUseException()
        }

        productItemRepository.delete(item)
        return "Item deleted successfully"
    }

    @Transactional
    fun updateProductItem(
        productId: UUID,
        itemId: UUID,
        request: ProductItemUpdateRequest
    ): ProductItem {

        val item = productItemRepository.findByIdOrNull(itemId)
            ?: throw ProductItemNotFoundException()

        if (item.product.id != productId) {
            throw ProductItemNotFoundException()
        }

        val color = request.colorId?.let {
            colorRepository.findByIdOrNull(it)
        }
        val size = request.sizeId?.let {
            sizeRepository.findByIdOrNull(it)
        }

        val discount = request.discountId?.let {
            discountRepository.findByIdOrNull(it)
        }

        val updatedItem = item.copy(
            price = request.price ?: item.price,
            stock = request.stock ?: item.stock,
            variationImageUrl = request.variationImageUrl ?: item.variationImageUrl,
            color = color,
            size = size,
            discount = discount
        )

        val savedItem = productItemRepository.save(updatedItem)
        return savedItem
    }

    private fun removeBaseItemIfExists(product: Product) {
        val items = productItemRepository.findAllByProductIdWithDetails(product.id!!)
        if (items.size == 1) {
            val single = items.first()
            val isBaseItem = single.color == null && single.size == null
                    && single.weight == null && single.discount == null
            if (isBaseItem) {
                productItemRepository.delete(single)
            }
        }
    }
}
