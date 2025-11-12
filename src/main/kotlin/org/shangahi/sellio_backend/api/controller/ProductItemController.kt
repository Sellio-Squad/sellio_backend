package org.shangahi.sellio_backend.api.controller

import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.ProductItemRequest
import org.shangahi.sellio_backend.api.dto.ProductItemResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.TrendingProductResponse
import org.shangahi.sellio_backend.api.mapper.toPagedResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.ProductItemService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

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

    @PostMapping("/{productId}/insert")
    fun insertProductItem(
        @PathVariable productId: UUID,
        @Valid @RequestBody request: ProductItemRequest
    ): ResponseEntity<ProductItemResponse> {
        val response = productItemService.insertProductItem(request, productId)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response.toResponse())
    }
}