package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.ProductItemRequest
import org.shangahi.sellio_backend.api.dto.request.ThriftProductRequest
import org.shangahi.sellio_backend.entity.Product
import org.shangahi.sellio_backend.entity.ProductImage
import org.shangahi.sellio_backend.entity.ProductItem
import org.shangahi.sellio_backend.entity.ProductSubCategory
import org.shangahi.sellio_backend.entity.ThriftProduct
import org.shangahi.sellio_backend.repository.*
import org.shangahi.sellio_backend.service.exception.CategoryNotFoundException
import org.shangahi.sellio_backend.service.exception.StoreNotFoundException
import org.shangahi.sellio_backend.service.exception.SubCategoryNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ThriftProductService(
    private val thriftProductRepository: ThriftProductRepository,
    private val storeRepository: StoreRepository,
    private val subCategoryRepository: SubCategoryRepository,
    private val productSubcategoryRepository: ProductSubcategoryRepository,
    private val productItemRepository: ProductItemRepository,
    private val productImageRepository: ProductImageRepository,
    private val discountRepository: DiscountRepository,
    private val colorRepository: ColorRepository,
    private val sizeRepository: SizeRepository,
    private val weightRepository: WeightRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    fun createThriftProduct(request: ThriftProductRequest): ThriftProduct {
        val store = storeRepository.findById(request.storeId)
            .orElseThrow { StoreNotFoundException() }

        checkSubCategoryIsExist(request.subCategoryIds)

        val product = ThriftProduct(
            condition = request.condition,
            defects = request.defects,
            title = request.title,
            description = request.description,
            mainImageURL = request.mainImageURL,
            store = store
        )
        product.items = createProductItems(request.items, product, request.price)
        val savedProduct = thriftProductRepository.save(product)

        createProductSubCategories(request.subCategoryIds, savedProduct)
        createProductImages(request.imageUrls, savedProduct)

        return savedProduct
    }

    @Transactional(readOnly = true)
    fun getAllThriftProducts(
        categoryId: UUID?,
        pageable: Pageable
    ): Page<ThriftProduct> {
        if (categoryId != null && !categoryRepository.existsById(categoryId)) {
            throw CategoryNotFoundException()
        }
        return if (categoryId != null) {
            thriftProductRepository.findByCategoryId(categoryId, pageable)
        } else {
            thriftProductRepository.findAll(pageable)
        }
    }


    private fun checkSubCategoryIsExist(subCategoryIds: List<UUID>) {
        subCategoryIds.forEach { subCategoryId ->
            if (!subCategoryRepository.existsById(subCategoryId)) {
                throw SubCategoryNotFoundException()
            }
        }
    }

    private fun createProductSubCategories(subCategoryIds: List<UUID>, savedProduct: ThriftProduct) {
        if (subCategoryIds.isNotEmpty()) {
            val subCategories = subCategoryRepository.findAllById(subCategoryIds)
            val productSubCategories = subCategories.map { sub ->
                ProductSubCategory(product = savedProduct, subCategory = sub)
            }
            productSubcategoryRepository.saveAll(productSubCategories)
        }
    }

    private fun createProductImages(imageUrls: List<String>, savedProduct: ThriftProduct) {
        if (imageUrls.isNotEmpty()) {
            val images = imageUrls.map { url ->
                ProductImage(product = savedProduct, imageUrl = url)
            }
            productImageRepository.saveAll(images)
        }
    }


    private fun createProductItems(
        items: List<ProductItemRequest>?,
        product: Product,
        defaultPrice: Double
    ): Set<ProductItem> {
        return if (items != null && items.isNotEmpty()) {
            items.map { item ->
                ProductItem(
                    product = product,
                    price = item.price,
                    discount = item.discountId?.let { id -> discountRepository.findById(id).orElse(null) },
                    color = item.colorId?.let { id -> colorRepository.findById(id).orElse(null) },
                    size = item.sizeId?.let { id -> sizeRepository.findById(id).orElse(null) },
                    weight = item.weightId?.let { id -> weightRepository.findById(id).orElse(null) },
                    stock = item.stock
                )
            }
        } else {
            listOf(
                ProductItem(
                    product = product,
                    price = defaultPrice,
                    stock = 0
                )
            )
        }.toSet()
    }
}