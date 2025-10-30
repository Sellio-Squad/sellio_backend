package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.ProductCardResponse
import org.shangahi.sellio_backend.api.dto.StoreDetailsResponse
import org.shangahi.sellio_backend.entity.Store

fun Store.toStoreDetailsResponse(featuredProducts: List<ProductCardResponse>) = StoreDetailsResponse(
    id = this.id ?: throw IllegalStateException("Store ID was null for Store ${this.title}"),
    ownerId = this.owner.id ?: throw IllegalStateException("Store owner ID was null for Store ${this.title}"),
    title = this.title,
    description = this.description,
    avatarImageURL = this.avatarImageURL,
    coverImageURL = this.coverImageURL,
    featuredProducts = featuredProducts,
    phoneNumber = this.phoneNumber,
    city = this.city,
    government = this.government,
    country = this.country,
)