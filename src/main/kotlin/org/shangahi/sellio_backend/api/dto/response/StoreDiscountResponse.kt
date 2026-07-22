package org.shangahi.sellio_backend.api.dto.response

import org.shangahi.sellio_backend.entity.Discount
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class StoreDiscountResponse(
    val id: UUID,
    val type: Discount.DiscountType = Discount.DiscountType.PERCENTAGE,
    val value: BigDecimal,
    val endDate: Instant?
)