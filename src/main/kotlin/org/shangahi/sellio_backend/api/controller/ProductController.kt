package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.ProductCardResponse
import org.shangahi.sellio_backend.api.dto.ProductRequest
import org.shangahi.sellio_backend.api.dto.ProductResponse
import org.shangahi.sellio_backend.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

// GET http://localhost:8080/v1/stores/store_id/products
// GET http://localhost:8080/v1/stores/store_id/products?page=0&size=2
@RestController
@RequestMapping("/v1/stores/{storeId}/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun getAllProductsForStore(
        @PathVariable storeId: UUID,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): Page<ProductCardResponse> {
        return productService.getStoreProducts(storeId, page, size)
    }

    @PostMapping("/create")
    fun create(@RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        val saved = productService.create(request)
        return ResponseEntity.ok(saved)
    }
}