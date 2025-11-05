package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.response.DiscountResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.service.DiscountService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
//  http://localhost:8080/v1/discounts/store/{storeId}
//  http://localhost:8080/v1/discounts/product/{productId}
//  http://localhost:8080/v1/discounts/category/{subcategoryId}
//  http://localhost:8080/v1/discounts/sub-category/{subcategoryId}

@RequestMapping("/v1/discounts")
class DiscountController(
    private val discountService: DiscountService
) {

    @GetMapping("/store/{storeId}")
    fun getStoreDiscounts(
        @PathVariable("storeId") storeId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<DiscountResponse> {
        return discountService.getDiscountsByStoreId(storeId, pageable)
    }

    @GetMapping("/product/{productId}")
    fun getProductDiscounts(
        @PathVariable("productId") productId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<DiscountResponse> {
        return discountService.getDiscountsByProductId(productId, pageable)
    }

    @GetMapping("/category/{CategoryId}")
    fun getCategoryDiscounts(
        @PathVariable("CategoryId") categoryId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<DiscountResponse> {
        return discountService.getDiscountsByCategoryId(categoryId, pageable)
    }

    @GetMapping("/sub-category/{subCategoryId}")
    fun getSubCategoryDiscounts(
        @PathVariable("subCategoryId") subCategoryId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<DiscountResponse> {
        return discountService.getDiscountsBySubCategoryId(subCategoryId, pageable)
    }

}