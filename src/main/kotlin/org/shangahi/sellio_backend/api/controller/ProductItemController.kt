package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.TrendingProductResponse
import org.shangahi.sellio_backend.api.mapper.toPagedResponse
import org.shangahi.sellio_backend.service.ProductItemService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/product-items")
class ProductItemController(
    private val productItemService: ProductItemService
) {
    @GetMapping("/trending")
    fun getTrendingProducts(
        @ParameterObject
        @PageableDefault(page = 0, size = 20)
        pageable: Pageable
    ): PageResponse<TrendingProductResponse> {
        val trendingProducts = productItemService.getTrendingProducts(pageable)
        return trendingProducts.toPagedResponse()
    }
}