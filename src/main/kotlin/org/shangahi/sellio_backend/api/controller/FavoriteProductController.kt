package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.request.FavoriteProductRequest
import org.shangahi.sellio_backend.api.dto.response.FavoriteProductsResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.swagger.FavoriteProductDocs
import org.shangahi.sellio_backend.service.FavoriteProductService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/favorite-products")
@Tag(name = "Favorite Products", description = "Endpoints for managing user favorite products")
class FavoriteProductController(
    private val favoriteProductService: FavoriteProductService
) {
    @FavoriteProductDocs.GetFavoriteProducts
    @GetMapping("/{userId}")
    fun findByUserId(
        @PathVariable userId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<FavoriteProductsResponse> {
        return favoriteProductService.getFavoriteProductsByUserId(userId,pageable)
    }


    @FavoriteProductDocs.ToggleFavoriteProduct
    @PostMapping("/toggle")
    fun toggleFavorite(
        @RequestBody request: FavoriteProductRequest
    ): ResponseEntity<String> {
        val message = favoriteProductService.toggleFavorite(request)
        return ResponseEntity.ok(message)
    }
}
