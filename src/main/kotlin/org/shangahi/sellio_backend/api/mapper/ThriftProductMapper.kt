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
    minPrice = this.getMinPrice(),
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
        condition = this.condition,
        defects = this.defects
    )

fun ThriftProduct.toResponse(isFavorite: Boolean = false): ThriftProductResponse =
    ThriftProductResponse(
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
        items = this.items.map { it.toResponse() },
        condition = this.condition,
        defects = this.defects
    )
