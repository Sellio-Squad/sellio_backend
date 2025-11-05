package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.response.FavoriteProductsResponse
import org.shangahi.sellio_backend.service.FavoriteProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

// http://localhost:8080/v1/favorite-products/userId
@RestController
@RequestMapping("/v1/favorite-products")
class FavoriteProductController(private val favoriteProductService: FavoriteProductService) {

    @GetMapping("/{userId}")
    fun findByUserId(@PathVariable userId: UUID): List<FavoriteProductsResponse> {

        return favoriteProductService.getFavoriteProductsByUserId(userId)
    }
}