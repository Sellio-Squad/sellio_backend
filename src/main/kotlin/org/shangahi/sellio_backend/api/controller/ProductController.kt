package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.ProductRequest
import org.shangahi.sellio_backend.api.dto.response.ProductResponse
import org.shangahi.sellio_backend.api.dto.request.ProductUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.doc.ProductDoc
import org.shangahi.sellio_backend.service.ProductService
import org.shangahi.sellio_backend.service.StorageService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/v1/products")
@Tag(name = "Product", description = "Endpoints for managing products")
class ProductController(
    private val productService: ProductService,
    private val storageService: StorageService
) {
    @ProductDoc.GetProductByStoreId
    @GetMapping("/store/{storeId}")
    fun getAllProductsForStore(
        @PathVariable storeId: UUID,
        @ParameterObject
        @PageableDefault(
            page = 0,
            size = 20,
            sort = ["isFeatured"],
            direction = Sort.Direction.DESC
        )
        pageable: Pageable
    ): PageResponse<ProductCardResponse> {
        return productService.getStoreProducts(storeId, pageable)
    }

    @ProductDoc.SearchByProductTitle
    @GetMapping("/search")
    fun searchProducts(
        @RequestParam("query", required = true) query: String,
        @RequestParam("city", required = false) city: String?,
        @ParameterObject
        @PageableDefault(page = 0, size = 20)
        pageable: Pageable
    ): PageResponse<ProductCardResponse> {

        return productService.searchProductsByTitle(query, city, pageable)
    }

    @ProductDoc.CreateProduct
    @PostMapping("/create")
    fun create(@Valid @RequestBody request: ProductRequest): ResponseEntity<ProductResponse> {
        val saved = productService.create(request).toResponse()
        return ResponseEntity.ok(saved)
    }

    @ProductDoc.UpdateProduct
    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: UUID,
        @Valid @RequestBody request: ProductUpdateRequest
    ): ResponseEntity<ProductResponse> {
        val saved = productService.updateProduct(id, request)
        return ResponseEntity.ok(saved.toResponse())
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

    @ProductDoc.Thrift
    @GetMapping("/used")
    fun getUsedProducts(
        @ParameterObject
        @PageableDefault(page = 0, size = 20, direction = Sort.Direction.DESC)
        pageable: Pageable
    ): PageResponse<ProductResponse> {
        return productService.getUsedProducts(pageable).toPageResponse { it.toResponse() }
    }

    @ProductDoc.GetProductById
    @GetMapping("/{productId}")
    fun getProductById(
        @AuthenticationPrincipal userId: UUID?,
        @PathVariable productId: UUID
    ): ResponseEntity<ProductResponse> {
        val product = productService.getProductById(productId, userId)
        return ResponseEntity.ok(product)
    }

    @GetMapping("/{categoryId}/custom")
    fun getCustomProductsByCategory(
        @PathVariable categoryId: UUID,
        @ParameterObject @PageableDefault(size = 20) pageable: Pageable
    ): PageResponse<ProductResponse> {
        return productService.getCustomProductsByCategory(categoryId, pageable)
    }

    @PostMapping("/custom-design")
    fun uploadCustomizationImage(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<String> {

        val fileName = UUID.randomUUID().toString()
        val imageUrl = storageService.uploadImage(
            file = file,
            fileName = fileName,
            folderName = "custom"
        )
        return ResponseEntity.ok(imageUrl)
    }

    @GetMapping("/store/{storeId}/subcategory/{subCategoryId}")
    fun getProductsBySubcategoryAndStore(
        @PathVariable storeId: UUID,
        @PathVariable subCategoryId: UUID,
        @PageableDefault(
            page = 0,
            size = 10,
            direction = Sort.Direction.DESC
        )
        pageable: Pageable,
    ): PageResponse<ProductResponse> {
        val productsPage = productService.getProductsBySubCategoryAndStore(subCategoryId, storeId, pageable)
        return productsPage.toPageResponse { it.toResponse() }
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: UUID): ResponseEntity<String> {
        val message = productService.deleteProduct(id)
        return ResponseEntity.ok(message)
    }

}