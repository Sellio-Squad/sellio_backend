package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.ProductItemRequest
import org.shangahi.sellio_backend.api.dto.request.ProductRequest
import org.shangahi.sellio_backend.api.dto.request.ProductUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.dto.response.ProductResponse
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.util.SELLIO_STORE_ID
import org.shangahi.sellio_backend.entity.Product
import org.shangahi.sellio_backend.entity.ProductImage
import org.shangahi.sellio_backend.entity.ProductItem
import org.shangahi.sellio_backend.entity.ProductSubCategory
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.repository.*
import org.shangahi.sellio_backend.security.SecurityUtils
import org.shangahi.sellio_backend.service.exception.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
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
    private val weightRepository: WeightRepository,
    private val storageService: StorageService,
    private val orderItemRepository: OrderItemRepository,
    private val favoriteProductRepository: FavoriteProductRepository,
    private val categoryRepository: CategoryRepository,
) {
    @Transactional(readOnly = true)
    fun getProductsBySubCategoryId(subCategoryId: UUID, pageable: Pageable): PageResponse<ProductCardResponse> {
        if (!subCategoryRepository.existsById(subCategoryId)) {
            throw SubCategoryNotFoundException()
        }
        val productPage = productRepository.findBySubCategoryId(subCategoryId, pageable)
        return mapPageToResponseWithFavorites(productPage)
    }

    @Transactional(readOnly = true)
    fun getProductsByCategoryId(categoryId: UUID, pageable: Pageable): PageResponse<ProductCardResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw CategoryNotFoundException()
        }
        val productPage = productRepository.findByCategoryId(categoryId, pageable)
        return mapPageToResponseWithFavorites(productPage)
    }

    @Transactional(readOnly = true)
    fun getStoreProducts(storeId: UUID, pageable: Pageable): PageResponse<ProductCardResponse> {
        if (!storeRepository.existsById(storeId)) {
            throw StoreNotFoundException()
        }
        val productPage = productRepository.findAllByStoreId(storeId, pageable)
        return mapPageToResponseWithFavorites(productPage)

    }

    fun searchProductsByTitle(
        title: String,
        city: String?,
        pageable: Pageable
    ): PageResponse<ProductCardResponse> {
        val trimmedTitle = title.trim()

        if (trimmedTitle.isBlank()) {
            return mapPageToResponseWithFavorites(Page.empty(pageable))
        }
        val productPage = if (city.isNullOrBlank()) {
            productRepository.findByTitleContainingIgnoreCase(title, pageable)
        } else {
            productRepository.findByTitleContainingIgnoreCaseAndStoreCityIgnoreCase(title, city, pageable)
        }
        return mapPageToResponseWithFavorites(productPage)

    }

    @Transactional
    fun create(request: ProductRequest): Product {
        if (productRepository.existsByTitle(request.title)) {
            throw ProductAlreadyExistException()
        }

        checkSubCategoryIsExist(request.subCategoryIds)

        val store = storeRepository.findById(request.storeId)
            .orElseThrow { StoreNotFoundException() }

        val product = request.toEntity(store)
        product.items = createProductItems(request.items, product, request.price)
        val savedProduct = productRepository.save(product)

        createProductSubCategories(request.subCategoryIds, savedProduct)
        createProductImages(request.imageUrls, savedProduct)

        val fullProduct = productRepository.findByIdWithItems(savedProduct.id!!)
            ?: throw ProductSavingException()
        return fullProduct
    }


    @Transactional
    fun updateProduct(productId: UUID, request: ProductUpdateRequest): Product {

        val existingProduct = productRepository.findByIdOrNull(productId)
            ?: throw ProductNotFoundException()
        if (request.title != null && request.title != existingProduct.title) {
            if (productRepository.existsByTitleAndIdNot(request.title, existingProduct.id!!)) {
                throw ProductAlreadyExistException()
            }
            existingProduct.title = request.title
        }

        request.description?.let { existingProduct.description = it }
        request.mainImageURL?.let { existingProduct.mainImageURL = it }
        request.isUsed?.let { existingProduct.isUsed = it }
        request.isFeatured?.let { existingProduct.isFeatured = it }

        existingProduct.items = createProductItems(request.items, existingProduct)

        val savedProduct = productRepository.save(existingProduct)

        request.items?.let {
            productItemRepository.deleteAll(existingProduct.items)
        }

        request.subCategoryIds?.let {
            productSubcategoryRepository.deleteAll(existingProduct.productSubCategories)
            createProductSubCategories(request.subCategoryIds, savedProduct)
        }

        val product = productRepository.findByIdWithItems(savedProduct.id!!)
            ?: throw ProductSavingException()
        return product
    }

    @Transactional
    fun uploadProductImage(
        productId: UUID,
        mainImage: MultipartFile,
        images: List<MultipartFile>
    ): List<String> {
        val product = productRepository.findById(productId)
            .orElseThrow { ProductSavingException() }

        val uploadedUrls = mutableListOf<String>()
        val mainImageUploadedUrl = storageService.uploadImage(
            file = mainImage,
            fileName = product.title,
            folderName = "products/mainImage"
        )
        val mainProductImage = ProductImage(product = product, imageUrl = mainImageUploadedUrl)
        productImageRepository.save(mainProductImage)
        uploadedUrls.add(mainImageUploadedUrl)

        images.forEach { image ->
            if (!image.isEmpty) {
                val uploadedUrl = storageService.uploadImage(
                    file = image,
                    fileName = product.title,
                    folderName = "products/images"
                )
                val productImage = ProductImage(product = product, imageUrl = uploadedUrl)
                productImageRepository.save(productImage)
                uploadedUrls.add(uploadedUrl)
            }
        }

        return uploadedUrls
    }

    @Transactional
    fun deleteProductImage(productId: UUID, imageId: UUID, imageUrl: String): Boolean {
        val productImage = productImageRepository.findById(imageId)
            .orElseThrow { ProductSavingException() }

        if (productImage.product.id != productId) {
            throw ProductSavingException()
        }

        val deleted = storageService.deleteImage(productImage.imageUrl)
        if (deleted) {
            productImageRepository.delete(productImage)
        }
        return deleted
    }

    private fun createProductSubCategories(subCategoryIds: List<UUID>, savedProduct: Product) {
        if (subCategoryIds.isNotEmpty()) {
            val subCategories = subCategoryRepository.findAllById(subCategoryIds)
            val productSubCategories = subCategories.map { sub ->
                ProductSubCategory(product = savedProduct, subCategory = sub)
            }
            productSubcategoryRepository.saveAll(productSubCategories)
        }
    }

    private fun createProductImages(imageUrls: List<String>, savedProduct: Product) {
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
        defaultPrice: Double? = null
    ): Set<ProductItem> {
        val basePrice = defaultPrice ?: product.items.firstOrNull()?.price ?: throw ProductBasePriceException()

        val baseItem = ProductItem(
            product = product,
            price = basePrice,
            stock = items?.firstOrNull()?.stock ?: 0
        )

        return if (items != null && items.isNotEmpty()) {
            val variationItems = items.map { item ->
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
            (listOf(baseItem) + variationItems).toSet()
        } else {
            setOf(baseItem)
        }
    }
    @Transactional(readOnly = true)
    fun getUsedProducts(pageable: Pageable): Page<Product> {
        return productRepository.findAllUsedProductsWithDetails(pageable)
    }

    @Transactional(readOnly = true)
    fun getProductById(
        productId: UUID,
        userId: UUID?
    ): ProductResponse {
        val product = productRepository.findByIdWithItems(productId)
            ?: throw ProductNotFoundException()

        val isFavorite = userId?.let {
            favoriteProductRepository
                .findByUserIdAndProductId(it, productId) != null
        } ?: false

        return product.toResponse(isFavorite)
    }

    @Transactional(readOnly = true)
    fun getCustomProductsByCategory(categoryId: UUID, pageable: Pageable): PageResponse<ProductResponse> {

        val productPage = productRepository.findCustomProductsByStoreAndCategory(
            SELLIO_STORE_ID,
            categoryId,
            pageable
        )

        return productPage.toPageResponse { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getTrendingProducts(userId: UUID?, pageable: Pageable): PageResponse<ProductCardResponse> {
        val trendingProducts =
            productItemRepository.findTrendingProducts(OrderStatus.COMPLETED, pageable)
                .takeIf { !it.isEmpty }
                ?: productItemRepository.findAllProducts(pageable)

        val productIds = trendingProducts.content.map { it.productId }
        val productsById = productRepository.findAllByIdWithItems(productIds).associateBy { it.id }

        val favoriteIds = if (userId != null && productIds.isNotEmpty()) {
            favoriteProductRepository.findFavoriteProductIdsByUserIdAndProductIds(userId, productIds)
        } else {
            emptySet()
        }

        return trendingProducts.toPageResponse { trendingProduct ->
            val product = productsById[trendingProduct.productId]
                ?: throw ProductNotFoundException()

            product.toProductCardResponse(
                isFavorite = favoriteIds.contains(trendingProduct.productId)
            )
        }
    }

    @Transactional(readOnly = true)
    fun getProductsBySubCategoryAndStore(
        subCategoryId: UUID,
        storeId: UUID,
        pageable: Pageable
    ): Page<Product> {
        return productRepository.findBySubCategoryAndStore(subCategoryId, storeId, pageable)
    }


    @Transactional
    fun deleteProduct(productId: UUID): String {
        val product = productRepository.findByIdWithItems(productId) ?: throw ProductNotFoundException()
        val isOrdered = product.items.any { orderItemRepository.existsByProductItemId(it.id!!) }

        if (isOrdered) {
            throw ProductItemInUseException()
        }
        if (product.mainImageURL != null) {
            storageService.deleteImage(product.mainImageURL!!)
        }
        product.items.forEach { item ->
            item.variationImageUrl?.let { storageService.deleteImage(it) }
        }
        product.images.forEach { image ->
            storageService.deleteImage(image.imageUrl)
        }
        discountRepository.deleteByProductId(productId)
        favoriteProductRepository.deleteByProductId(productId)
        productRepository.delete(product)
        return "Product deleted successfully"
    }


    private fun checkSubCategoryIsExist(subCategoryIds: List<UUID>) {
        subCategoryIds.forEach { subCategoryId ->
            if (!subCategoryRepository.existsById(subCategoryId)) {
                throw SubCategoryNotFoundException()
            }
        }
    }


    private fun mapPageToResponseWithFavorites(productPage: Page<Product>): PageResponse<ProductCardResponse> {

        if (productPage.isEmpty) {
            return productPage.toPageResponse { it.toProductCardResponse(false) }
        }

        val currentUserId = SecurityUtils.getCurrentUserId()

        val productIds = productPage.content.map { it.id!! }

        val favoriteIds = if (currentUserId != null) {
            favoriteProductRepository.findFavoriteProductIdsByUserIdAndProductIds(currentUserId, productIds)
        } else {
            emptySet()
        }

        return productPage.toPageResponse { product ->
            product.toProductCardResponse(
                isFavorite = favoriteIds.contains(product.id)
            )
        }
    }
}

