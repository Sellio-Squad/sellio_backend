package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.request.FavouriteStoreRequest
import org.shangahi.sellio_backend.api.dto.response.FavoriteStoreResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.FavoriteStoresDoc
import org.shangahi.sellio_backend.service.FavoriteStoreService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/favorite-stores")
@Tag(name = "Favorite Stores", description = "Endpoints for managing favorite stores")
class FavoriteStoreController(
    private val favoriteStoreService: FavoriteStoreService
) {
    @FavoriteStoresDoc.ToggleFavoriteStores
    @PostMapping("/toggle")
    fun toggleFavoriteStore(
        @RequestBody request: FavouriteStoreRequest
    ): FavoriteStoreResponse {
        return favoriteStoreService.toggleFavoriteStore(request).toResponse()
    }

    @FavoriteStoresDoc.GetFavoriteStores
    @GetMapping("/{userId}")
    fun getFavoriteStoresByUserId(
        @PathVariable userId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<FavoriteStoreResponse> {
        val favoriteStores = favoriteStoreService.getFavoriteStoresByUserId(userId, pageable)
        return favoriteStores.toPageResponse()
    }
}