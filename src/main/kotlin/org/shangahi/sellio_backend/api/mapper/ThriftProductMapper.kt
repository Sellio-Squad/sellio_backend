package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.ThriftProductRequest
import org.shangahi.sellio_backend.api.dto.response.ThriftProductCardResponse
import org.shangahi.sellio_backend.api.dto.response.ThriftProductResponse
import org.shangahi.sellio_backend.entity.Store
import org.shangahi.sellio_backend.entity.ThriftProduct

fun ThriftProduct.toThriftProductCardResponse(
    isFavorite: Boolean
): ThriftProductCardResponse = ThriftProductCardResponse(
    id = this.id!!,
    title = this.title,
    price = this.price,
    mainImageUrl = this.mainImageURL,
    isFavorite = isFavorite,
    condition = condition
)

fun ThriftProductRequest.toEntity(store: Store): ThriftProduct =
    ThriftProduct(
        title = this.title,
        description = this.description,
        mainImageURL = this.mainImageURL,
        store = store,
        price = this.price,
        condition = this.condition,
        defects = this.defects
    )

fun ThriftProduct.toResponse(): ThriftProductResponse =
    ThriftProductResponse(
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
        items = this.items.map { it.toResponse() },
        isFavorite = false,
        condition = this.condition,
        defects = this.defects
    )

fun ThriftProduct.toResponse(isFavorite: Boolean): ThriftProductResponse =
    ThriftProductResponse(
        id = this.id!!,
        title = this.title,
        description = this.description,
        mainImageURL = this.mainImageURL,
        storeId = this.store.id!!,
        price = this.price,
        isUsed = this.isUsed,
        isFeatured = this.isFeatured,
        isFavorite = isFavorite,
        subCategoryIds = this.productSubCategories.mapNotNull { it.subCategory?.id },
        imageUrls = this.images.map { it.imageUrl },
        items = this.items.map { it.toResponse() },
        condition = this.condition,
        defects = this.defects
    )
