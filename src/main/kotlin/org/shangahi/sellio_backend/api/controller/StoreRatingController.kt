package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.StoreRatingResponse
import org.shangahi.sellio_backend.service.StoreRatingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

//GET http://localhost:8080/v1/stores/storeId/rating
@RestController
@RequestMapping("/v1/stores/{storeId}/rating")
class StoreRatingController(
    private val storeRatingService: StoreRatingService
) {
    @GetMapping
    fun getStoreRatingSummary(
        @PathVariable storeId: UUID
    ): StoreRatingResponse{

        return storeRatingService.getStoreRatingSummary(storeId)
    }
}