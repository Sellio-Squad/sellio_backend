package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.ProductCardResponse
import org.shangahi.sellio_backend.api.dto.ProductRequest
import org.shangahi.sellio_backend.api.dto.ProductResponse
import org.shangahi.sellio_backend.api.mapper.toDTO
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.entity.Product
import org.shangahi.sellio_backend.entity.ProductImage
import org.shangahi.sellio_backend.entity.ProductItem
import org.shangahi.sellio_backend.entity.ProductSubCategory
import org.shangahi.sellio_backend.repository.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val storeRepository: StoreRepository,
    private val subCategoryRepository: SubCategoryRepository,
    private val productImageRepository: ProductImageRepository,
    private val productItemRepository: ProductItemRepository,
    private val productSubcategoryRepository: ProductSubcategoryRepository,
    private val discountRepository: DiscountRepository,
    private val colorRepository: ColorRepository,
    private val sizeRepository: SizeRepository,
    private val weightRepository: WeightRepository
) {
    @Transactional(readOnly = true)
    fun getStoreProducts(storeId: UUID, page: Int, size: Int): Page<ProductCardResponse> {

        val sorting = Sort.by(Sort.Direction.DESC, "isFeatured")
        val pageable = PageRequest.of(page, size, sorting)
        val productPage = productRepository.findAllByStoreId(storeId, pageable)

        return productPage.map { product -> product.toProductCardResponse() }
    }

    @Transactional
    fun create(request: ProductRequest): ProductResponse {
        val store = storeRepository.findById(request.storeId)
            .orElseThrow { IllegalArgumentException("Store not found with id ${request.storeId}") }

        val product = request.toEntity(store)
        val savedProduct = productRepository.save(product)
        createProductSubCategories(request, savedProduct)
        createProductImages(request, savedProduct)
        createProductItems(request, savedProduct)

        val fullProduct = productRepository.findByIdWithRelations(savedProduct.id!!)
            ?: throw IllegalStateException("Product not found after saving")
        return fullProduct.toDTO()
    }

    private fun createProductSubCategories(request: ProductRequest, savedProduct: Product) {
        if (request.subCategoryIds.isNotEmpty()) {
            val subCategories = subCategoryRepository.findAllById(request.subCategoryIds)
            val productSubCategories = subCategories.map { sub ->
                ProductSubCategory(product = savedProduct, subCategory = sub)
            }
            productSubcategoryRepository.saveAll(productSubCategories)
        }
    }

    private fun createProductImages(request: ProductRequest, savedProduct: Product) {
        if (request.imageUrls.isNotEmpty()) {
            val images = request.imageUrls.map { url ->
                ProductImage(product = savedProduct, imageUrl = url)
            }
            productImageRepository.saveAll(images)
        }
    }

    private fun createProductItems(request: ProductRequest, savedProduct: Product) {
        if (request.items.isNotEmpty()) {
            val items = request.items.map { item ->
                ProductItem(
                    product = savedProduct,
                    price = item.price,
                    discount = item.discountId?.let { id -> discountRepository.findById(id).orElse(null) },
                    color = item.colorId?.let { id -> colorRepository.findById(id).orElse(null) },
                    size = item.sizeId?.let { id -> sizeRepository.findById(id).orElse(null) },
                    weight = item.weightId?.let { id -> weightRepository.findById(id).orElse(null) },
                    stock = item.stock
                )
            }
            productItemRepository.saveAll(items)
        }
    }
}