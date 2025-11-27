package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.swagger.doc.FavoriteProductsDoc
import org.shangahi.sellio_backend.service.FavoriteProductService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/favorite-products")
@Tag(name = "Favorite Products", description = "Endpoints for managing user favorite products")
class FavoriteProductController(
    private val favoriteProductService: FavoriteProductService
) {
    @FavoriteProductsDoc.GetFavoriteProducts
    @GetMapping
    fun findByUserId(
        @AuthenticationPrincipal userId: UUID,
        @ParameterObject
        @PageableDefault(page = 0, size = 20)
        pageable: Pageable
    ): PageResponse<ProductCardResponse> {
        val responsePage = favoriteProductService.getFavoriteProductsByUserId(userId, pageable)
        return responsePage.toPageResponse { it }
    }


    @FavoriteProductsDoc.ToggleFavoriteProduct
    @PostMapping("/toggle/{productId}")
    fun toggleFavorite(
        @PathVariable productId: UUID,
        @AuthenticationPrincipal userId: UUID
    ): ResponseEntity<String> {
        val message = favoriteProductService.toggleFavorite(productId, userId)
        return ResponseEntity.ok(message)
    }
}
