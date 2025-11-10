package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.response.DiscountResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.swagger.doc.DiscountDoc
import org.shangahi.sellio_backend.service.DiscountService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/discounts")
@Tag(name = "Discount", description = "Endpoints for managing Discounts," +
        " Retrieve Discount info info by storeId, categoryId, subCategoryId and productId")
class DiscountController(
    private val discountService: DiscountService
) {

    @DiscountDoc.GetDiscountByStoreId
    @GetMapping("/store/{storeId}")
    fun getStoreDiscounts(
        @PathVariable("storeId") storeId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<DiscountResponse> {
        return discountService.getDiscountsByStoreId(storeId, pageable)
    }

    @DiscountDoc.GetDiscountByProductId
    @GetMapping("/product/{productId}")
    fun getProductDiscounts(
        @PathVariable("productId") productId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<DiscountResponse> {
        return discountService.getDiscountsByProductId(productId, pageable)
    }

    @DiscountDoc.GetDiscountByCategoryId
    @GetMapping("/category/{CategoryId}")
    fun getCategoryDiscounts(
        @PathVariable("CategoryId") categoryId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<DiscountResponse> {
        return discountService.getDiscountsByCategoryId(categoryId, pageable)
    }

    @DiscountDoc.GetDiscountBySubCategoryId
    @GetMapping("/sub-category/{subCategoryId}")
    fun getSubCategoryDiscounts(
        @PathVariable("subCategoryId") subCategoryId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<DiscountResponse> {
        return discountService.getDiscountsBySubCategoryId(subCategoryId, pageable)
    }

}