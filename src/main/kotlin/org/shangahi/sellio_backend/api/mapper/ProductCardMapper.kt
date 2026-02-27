package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.ProductRequest
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.dto.response.ProductItemResponse
import org.shangahi.sellio_backend.api.dto.response.ProductResponse
import org.shangahi.sellio_backend.entity.Product
import org.shangahi.sellio_backend.entity.ProductItem
import org.shangahi.sellio_backend.entity.Store

fun Product.toProductCardResponse(
    isFavorite: Boolean
): ProductCardResponse = ProductCardResponse(
    id = this.id!!,
    title = this.title,
    minPrice = this.getMinPrice(),
    mainImageUrl = this.mainImageURL,
    subCategoriesIds = this.productSubCategories.mapNotNull { it.subCategory?.id },
    description = description ?: "",
    isFavorite = isFavorite
)

fun ProductRequest.toEntity(store: Store): Product =
    Product(
        title = this.title,
        description = this.description,
        mainImageURL = this.mainImageURL,
        store = store,
        isUsed = this.isUsed,
        isFeatured = this.isFeatured
    )

fun Product.toResponse(): ProductResponse =
    ProductResponse(
        id = this.id!!,
        title = this.title,
        description = this.description,
        mainImageURL = this.mainImageURL,
        storeId = this.store.id!!,
        minPrice = this.getMinPrice(),
        isUsed = this.isUsed,
        isFeatured = this.isFeatured,
        subCategoryIds = this.productSubCategories.mapNotNull { it.subCategory?.id },
        imageUrls = this.images.map { it.imageUrl },
        items = this.items.map { it.toResponse() },
        isFavorite = false
    )

fun Product.toResponse(isFavorite: Boolean): ProductResponse =
    ProductResponse(
        id = this.id!!,
        title = this.title,
        description = this.description,
        mainImageURL = this.mainImageURL,
        storeId = this.store.id!!,
        minPrice = this.getMinPrice(),
        isUsed = this.isUsed,
        isFeatured = this.isFeatured,
        isFavorite = isFavorite,
        subCategoryIds = this.productSubCategories.mapNotNull { it.subCategory?.id },
        imageUrls = this.images.map { it.imageUrl },
        items = this.items.map { it.toResponse() }
    )


fun ProductItem.toResponse(): ProductItemResponse {
    return ProductItemResponse(
        id = this.id!!,
        price = this.price,
        discountId = this.discount?.id,
        colorId = this.color?.id,
        sizeId = this.size?.id,
        stock = this.stock,
        variationImageUrl = this.variationImageUrl,
    )
}