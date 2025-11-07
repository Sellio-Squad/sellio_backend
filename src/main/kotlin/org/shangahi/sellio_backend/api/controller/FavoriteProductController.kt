package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.FavoriteToggleRequest
import org.shangahi.sellio_backend.api.dto.response.FavoriteProductsResponse
import org.shangahi.sellio_backend.api.swagger.FavoriteProductDocs
import org.shangahi.sellio_backend.service.FavoriteProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/favorite-products")
@Tag(name = "Favorite Products", description = "Endpoints for managing user favorite products")
class FavoriteProductController(
    private val favoriteProductService: FavoriteProductService
) {

    @FavoriteProductDocs.GetFavorites
    @GetMapping("/{userId}")
    fun findByUserId(
        @PathVariable userId: UUID
    ): List<FavoriteProductsResponse> {
        return favoriteProductService.getFavoriteProductsByUserId(userId)
    }


    @FavoriteProductDocs.ToggleFavorite
    @PostMapping("/toggle")
    fun toggleFavorite(
        @RequestBody request: FavoriteToggleRequest
    ): ResponseEntity<String> {
        val message = favoriteProductService.toggleFavorite(request)
        return ResponseEntity.ok(message)
    }
}
