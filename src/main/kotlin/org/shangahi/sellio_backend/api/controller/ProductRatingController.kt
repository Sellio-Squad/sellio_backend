package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.ProductRatingRequest
import org.shangahi.sellio_backend.api.dto.response.MessageResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductRatingResponse
import org.shangahi.sellio_backend.api.dto.response.ProductRatingSummaryResponse
import org.shangahi.sellio_backend.api.swagger.doc.ProductRatingDoc
import org.shangahi.sellio_backend.service.ProductRatingService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/product-rating")
@Tag(name = "Product rating", description = "Endpoints for managing product ratings")
class ProductRatingController(
    private val productRatingService: ProductRatingService
) {

    @ProductRatingDoc.AddRating
    @PostMapping("/{productId}")
    fun addRating(
        @PathVariable productId: UUID,
        @Valid @RequestBody request: ProductRatingRequest
    ): MessageResponse {
        return productRatingService.addRating(productId, request)
    }

    @ProductRatingDoc.GetProductRatings
    @GetMapping("/{productId}")
    fun getProductRatings(
        @PathVariable productId: UUID,
        pageable: Pageable
    ): PageResponse<ProductRatingResponse> {
        return productRatingService.getProductRatings(productId, pageable)
    }

    @ProductRatingDoc.GetProductRatingSummary
    @GetMapping("/{productId}/summary")
    fun getProductRatingSummary(
        @PathVariable productId: UUID
    ): ProductRatingSummaryResponse {
        return productRatingService.getProductRatingSummary(productId)
    }

    @ProductRatingDoc.DeleteRating
    @DeleteMapping("/{ratingId}")
    fun deleteRating(
        @PathVariable ratingId: UUID
    ) {
        productRatingService.deleteRating(ratingId)
    }
}
