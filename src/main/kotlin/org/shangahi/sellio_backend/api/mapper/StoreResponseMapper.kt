package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.dto.response.StoreDiscountResponse
import org.shangahi.sellio_backend.api.dto.response.StoreInfoResponse
import org.shangahi.sellio_backend.api.dto.response.StoreResponse
import org.shangahi.sellio_backend.entity.Store
import org.springframework.data.domain.Page

fun Store.toStoreDetailsResponse(
    featuredProducts: List<ProductCardResponse>,
    averageRating: Double,
    discounts : List<StoreDiscountResponse>
) = StoreInfoResponse(
    id = this.id ?: throw IllegalStateException("Store ID was null for Store ${this.title}"),
    ownerId = this.owner.id ?: throw IllegalStateException("Store owner ID was null for Store ${this.title}"),
    title = this.title,
    description = this.description,
    avatarImageURL = this.avatarImageURL,
    coverImageURL = this.coverImageURL,
    featuredProducts = featuredProducts,
    city = this.city,
    government = this.government,
    country = this.country,
    avgRating = averageRating,
    activeStoreDiscounts = discounts,
    storeContacts = this.contacts.map { it.toStoreContactResponse() }
)

fun Store.toStoreResponse(): StoreResponse {
    return StoreResponse(
        id = id,
        title = title,
        city = city,
        government = government,
        country = country,
        avatarImageURL = avatarImageURL,
        coverImageURL = coverImageURL
    )
}


fun Page<Store>.toResponse(): PageResponse<StoreResponse> {
    return PageResponse(
        data = this.content.map { it.toStoreResponse() },
        totalElements = this.totalElements,
        page = this.number,
        pageSize = this.size,
        totalPages = this.totalPages
    )
}