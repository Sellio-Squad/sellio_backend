package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.ProductItemRequest
import org.shangahi.sellio_backend.api.dto.request.ProductItemUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductItemResponse
import org.shangahi.sellio_backend.api.dto.response.TrendingProductResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.doc.ProductItemDoc
import org.shangahi.sellio_backend.service.ProductItemService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/product-items")
@Tag(name = "ProductItem", description = "Endpoints for managing product Items")
class ProductItemController(
    private val productItemService: ProductItemService
) {
    @ProductItemDoc.GetProductItems
    @GetMapping("/{productId}/items")
    fun getProductItems(
        @PathVariable productId: UUID
    ): List<ProductItemResponse> {
        val productItems = productItemService.getProductItems(productId)
        return productItems.map { it.toResponse() }
    }

    @ProductItemDoc.GetTrendingProducts
    @GetMapping("/trending")
    fun getTrendingProducts(
        @AuthenticationPrincipal userId: UUID?,
        @ParameterObject
        @PageableDefault(page = 0, size = 20)
        pageable: Pageable
    ): PageResponse<TrendingProductResponse> {
        val trendingProducts = productItemService.getTrendingProducts(userId, pageable)
        return trendingProducts
    }

    @ProductItemDoc.InsertProductItem
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

    @ProductItemDoc.AddProductItems
    @PostMapping("/{productId}/items")
    fun addProductItems(
        @PathVariable productId: UUID,
        @Valid @RequestBody requests: List<ProductItemRequest>
    ): ResponseEntity<List<ProductItemResponse>> {
        val responses = productItemService.addProductItems(productId, requests)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responses.map { it.toResponse() })
    }

    @ProductItemDoc.UpdateProductItem
    @PutMapping("/{productId}/items/{itemId}")
    fun updateProductItem(
        @PathVariable productId: UUID,
        @PathVariable itemId: UUID,
        @Valid @RequestBody request: ProductItemUpdateRequest
    ): ProductItemResponse {
        val updatedItem = productItemService.updateProductItem(productId, itemId, request)
        return updatedItem.toResponse()
    }

    @ProductItemDoc.DeleteProductItem
    @DeleteMapping("/{productId}/items/{itemId}")
    fun deleteProductItem(
        @PathVariable productId: UUID,
        @PathVariable itemId: UUID
    ): ResponseEntity<String> {
        val message = productItemService.deleteProductItem(productId, itemId)
        return ResponseEntity.ok(message)
    }
}