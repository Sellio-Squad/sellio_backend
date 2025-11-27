package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.request.StoreCardResponse
import org.shangahi.sellio_backend.api.dto.response.FavoriteStoreResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.doc.FavoriteStoresDoc
import org.shangahi.sellio_backend.service.FavoriteStoreService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/favorite-stores")
@Tag(name = "Favorite Stores", description = "Endpoints for managing favorite stores")
class FavoriteStoreController(
    private val favoriteStoreService: FavoriteStoreService
) {
    @FavoriteStoresDoc.ToggleFavoriteStores
    @PostMapping("/toggle/{storeId}")
    fun toggleFavoriteStore(
        @PathVariable storeId: UUID,
        @AuthenticationPrincipal userId: UUID
    ): FavoriteStoreResponse {
        return favoriteStoreService.toggleFavoriteStore(userId, storeId).toResponse()
    }

    @FavoriteStoresDoc.GetFavoriteStores
    @GetMapping
    fun getFavoriteStoresByUserId(
        @AuthenticationPrincipal userId: UUID,
        @ParameterObject
        @PageableDefault(page = 0, size = 20)
        pageable: Pageable
    ): PageResponse<StoreCardResponse> {
        val favoriteStoresPage = favoriteStoreService.getFavoriteStoresByUserId(userId, pageable)
        return favoriteStoresPage.toPageResponse { it }
    }
}