package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.DiscountResponse
import org.shangahi.sellio_backend.entity.Discount

fun Discount.toDiscountResponse(): DiscountResponse {
    return DiscountResponse(
        id = this.id?: throw NullPointerException("Discount id is null"),
        storeId = this.store?.id,
        productId = this.product?.id,
        subCategoryId = this.subCategory?.id,
        type = this.type.name,
        value = this.value,
        startDate = this.startDate,
        endDate = this.endDate
    )
}