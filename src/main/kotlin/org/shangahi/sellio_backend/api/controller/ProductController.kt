package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

// GET http://localhost:8080/v1/product/store/store_id
// GET http://localhost:8080/v1/product/store/store_id?page=0&size=2
// GET http://localhost:8080/v1/product/search?query="laptop"
@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/store/{storeId}")
    fun getAllProductsForStore(
        @PathVariable storeId: UUID,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): Page<ProductCardResponse> {
        return productService.getStoreProducts(storeId, page, size)
    }


    @GetMapping("/search")
    fun searchProducts(
        @RequestParam("query") query: String,
        pageable: Pageable
    ): PageResponse<ProductCardResponse> {

        return productService.searchProductsByTitle(query, pageable)
    }
}