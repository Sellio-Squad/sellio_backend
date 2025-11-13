package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.response.StoreRatingResponse
import org.shangahi.sellio_backend.api.swagger.doc.StoreRatingDoc
import org.shangahi.sellio_backend.service.StoreRatingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/store-rating")
@Tag(name = "Store rating", description = "Endpoints for managing store Ratings")
class StoreRatingController(
    private val storeRatingService: StoreRatingService
) {
    @StoreRatingDoc.GetRatingInfo
    @GetMapping("/{storeId}")
    fun getStoreRatingSummary(
        @PathVariable storeId: UUID
    ): StoreRatingResponse {
        return storeRatingService.getStoreRatingSummary(storeId)
    }
}