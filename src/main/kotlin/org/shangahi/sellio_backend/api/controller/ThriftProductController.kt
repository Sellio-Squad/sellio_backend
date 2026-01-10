package org.shangahi.sellio_backend.api.controller

import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.ThriftProductRequest
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ThriftProductResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.ThriftProductService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/v1/thrift-products")
class ThriftProductController(
    private val thriftProductService: ThriftProductService
) {
    @PostMapping
    fun createThriftProduct(@RequestBody @Valid request: ThriftProductRequest): ResponseEntity<ThriftProductResponse> {
        val createdProduct = thriftProductService.createThriftProduct(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct.toResponse())
    }

    @GetMapping
    fun getAllThriftProducts(
        @RequestParam(required = false) categoryId: UUID?,
        @PageableDefault(page = 0, size = 20) pageable: Pageable
    ): ResponseEntity<PageResponse<ThriftProductResponse>> {
        val products = thriftProductService.getAllThriftProducts(categoryId, pageable)
        return ResponseEntity.ok(products.toPageResponse { it.toResponse() })
    }

}