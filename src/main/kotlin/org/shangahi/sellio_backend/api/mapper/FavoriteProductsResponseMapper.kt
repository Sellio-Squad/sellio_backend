package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.FavoriteProductsResponse
import org.shangahi.sellio_backend.entity.FavoriteProduct
import java.time.Instant

fun FavoriteProduct.toFavoriteProductsResponse(): FavoriteProductsResponse = FavoriteProductsResponse(
    id = this.id ?: throw IllegalStateException("favorite ID was null for user ${this.user.id}"),
    productId = this.product.id ?: throw IllegalStateException("product ID was null for user ${this.user.id}"),
    userId = this.user.id ?: throw IllegalStateException("user ID was null for product ${this.product.id}"),
    createdAt = this.createdAt?: Instant.now(),
)