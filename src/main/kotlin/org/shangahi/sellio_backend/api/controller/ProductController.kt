package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.ProductRequest
import org.shangahi.sellio_backend.api.dto.ProductResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.ProductService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

// GET http://localhost:8080/v1/products/store/store_id
// GET http://localhost:8080/v1/products/store/store_id?page=0&size=2
// GET http://localhost:8080/v1/products/search?query="laptop"
@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/store/{storeId}")
    fun getAllProductsForStore(
        @PathVariable storeId: UUID,
        @PageableDefault(page = 0, size = 20, sort = ["isFeatured"], direction = Sort.Direction.DESC) pageable: Pageable
    ): PageResponse<ProductCardResponse> {
        return productService.getStoreProducts(storeId, pageable)
    }


    @GetMapping("/search")
    fun searchProducts(
        @RequestParam("query", required = true) query: String,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<ProductCardResponse> {

        return productService.searchProductsByTitle(query, pageable)
    }

    @PostMapping("/create")
    fun create(@RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        val saved = productService.create(request)
        return ResponseEntity.ok(saved)
    }

    @PostMapping("/{productId}/images")
    fun uploadProductImages(
        @PathVariable productId: UUID,
        @RequestPart("main") mainImage: MultipartFile,
        @RequestPart("images") images: List<MultipartFile>
    ): ResponseEntity<List<String>> {
        val updatedProduct = productService.uploadProductImage(productId, mainImage, images)
        return ResponseEntity.ok(updatedProduct)
    }

    @GetMapping("/used")
    fun getUsedProducts(
        @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC)
        pageable: Pageable
    ): PageResponse<ProductResponse> = productService.getUsedProducts(pageable).toPageResponse { it.toResponse() }

    @GetMapping("/{productId}")
    fun getProductById(@PathVariable productId: UUID): ResponseEntity<ProductResponse> {
        val product = productService.getProductById(productId)
        return ResponseEntity.ok(product.toResponse())

    }


}