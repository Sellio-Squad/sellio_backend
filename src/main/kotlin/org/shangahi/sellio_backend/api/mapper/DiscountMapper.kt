package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.DiscountResponse
import org.shangahi.sellio_backend.api.dto.response.StoreDiscountResponse
import org.shangahi.sellio_backend.entity.Discount

fun Discount.toDiscountResponse(): DiscountResponse {
    return DiscountResponse(
        id = this.id!!,
        storeId = this.store?.id,
        productId = this.product?.id,
        subCategoryId = this.subCategory?.id,
        type = this.type.name,
        value = this.value,
        startDate = this.startDate,
        endDate = this.endDate
    )
}

fun Discount.toStoreDiscountResponse(): StoreDiscountResponse {
    return StoreDiscountResponse(
        id = this.id!!,
        type = this.type,
        value = this.value,
        endDate = this.endDate,
    )
}