package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.ProductItemResponse
import org.shangahi.sellio_backend.api.dto.ProductRequest
import org.shangahi.sellio_backend.api.dto.ProductResponse
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.entity.Product
import org.shangahi.sellio_backend.entity.ProductItem
import org.shangahi.sellio_backend.entity.Store

fun Product.toProductCardResponse(): ProductCardResponse = ProductCardResponse(
    id = this.id ?: throw IllegalStateException("Product ID was null for product ${this.title}"),
    title = this.title,
    price = this.price,
    mainImageUrl = this.mainImageURL,
)

fun ProductRequest.toEntity(store: Store): Product =
    Product(
        title = this.title,
        description = this.description,
        mainImageURL = this.mainImageURL,
        store = store,
        price = this.price,
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
        price = this.price,
        isUsed = this.isUsed,
        isFeatured = this.isFeatured,
        subCategoryIds = this.productSubCategories.mapNotNull { it.subCategory?.id },
        imageUrls = this.images.map { it.imageUrl },
        items = this.items.map { it.toResponse() }
    )

fun ProductItem.toResponse(): ProductItemResponse =
    ProductItemResponse(
        id = this.id!!,
        price = this.price,
        discountId = this.discount?.id,
        colorId = this.color?.id,
        sizeId = this.size?.id,
        weightId = this.weight?.id,
        stock = this.stock
    )