package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.FavoriteToggleRequest
import org.shangahi.sellio_backend.api.dto.response.FavoriteProductsResponse
import org.shangahi.sellio_backend.service.FavoriteProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

// http://localhost:8080/v1/favorite-products/userId
// http://localhost:8080/v1/favorite-products/toggle

@RestController
@RequestMapping("/v1/favorite-products")
class FavoriteProductController(private val favoriteProductService: FavoriteProductService) {

    @GetMapping("/{userId}")
    fun findByUserId(@PathVariable userId: UUID): List<FavoriteProductsResponse> {

        return favoriteProductService.getFavoriteProductsByUserId(userId)
    }

    @PostMapping("/toggle")

    fun toggleFavorite(@RequestBody request: FavoriteToggleRequest): ResponseEntity<String> {

        val message = favoriteProductService.toggleFavorite(request)

        return ResponseEntity.ok(message)
    }

}