package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.ProductCardResponse
import org.shangahi.sellio_backend.entity.Product

fun Product.toProductCardResponse(): ProductCardResponse = ProductCardResponse(
    id = this.id ?: throw IllegalStateException("Product ID was null for product ${this.title}"),
    title = this.title,
    price = this.price,
    mainImageUrl = this.mainImageURL,
)