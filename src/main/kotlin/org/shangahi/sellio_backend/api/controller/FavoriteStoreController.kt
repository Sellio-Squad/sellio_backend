package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.FavoriteStoreResponse
import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.service.FavoriteStoreService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/stores")
class FavoriteStoreController(
    private val favoriteStoreService: FavoriteStoreService
) {

    @GetMapping("/favorite")
    fun getFavoriteStoresByUserId(
        @RequestParam userId: UUID,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): PageResponse<FavoriteStoreResponse> {
        val favoriteStores = favoriteStoreService.getFavoriteStoresByUserId(userId, pageable)
        return favoriteStores.toPageResponse()
    }
}