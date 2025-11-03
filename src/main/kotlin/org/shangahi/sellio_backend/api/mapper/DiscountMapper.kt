package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.DiscountResponse
import org.shangahi.sellio_backend.entity.Discount
import org.shangahi.sellio_backend.service.exception.InternalServerErrorException

fun Discount.toDiscountResponse(): DiscountResponse {
    return DiscountResponse(
        id = this.id?: throw InternalServerErrorException("Discount ID was null during mapping for entity."),
        storeId = this.store?.id,
        productId = this.product?.id,
        subCategoryId = this.subCategory?.id,
        type = this.type.name,
        value = this.value,
        startDate = this.startDate,
        endDate = this.endDate
    )
}