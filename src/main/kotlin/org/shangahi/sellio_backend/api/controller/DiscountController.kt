package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.DiscountResponse
import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.service.DiscountService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
//  http://localhost:8080/v1/stores/discounts/{storeId}
@RequestMapping("/v1/stores/discounts")
class DiscountController(
    private val discountService: DiscountService
) {

    @GetMapping("/{storeId}")
    fun getStoreDiscounts(
        @PathVariable storeId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<DiscountResponse> {
        return discountService.getDiscountsByStoreId(storeId, pageable)
    }
}