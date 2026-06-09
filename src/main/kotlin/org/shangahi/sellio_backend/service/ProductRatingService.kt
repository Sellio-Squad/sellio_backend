package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.ProductRatingRequest
import org.shangahi.sellio_backend.api.dto.response.MessageResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductRatingResponse
import org.shangahi.sellio_backend.api.dto.response.ProductRatingSummaryResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.entity.ProductRating
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.repository.OrderItemRepository
import org.shangahi.sellio_backend.repository.ProductRatingRepository
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.security.SecurityUtils
import org.shangahi.sellio_backend.service.exception.ProductNotFoundException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductRatingService(
    private val productRatingRepository: ProductRatingRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val orderItemRepository: OrderItemRepository
) {
@Transactional
fun addRating(productId: UUID, request: ProductRatingRequest): MessageResponse {
    val userId = SecurityUtils.getCurrentUserId() ?: throw UserNotFoundException()

    val product = productRepository.findByIdOrNull(productId) ?: throw ProductNotFoundException()
    val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException()

    val canReview = checkReviewEligibility(userId, productId)
    if (!canReview) {
        throw IllegalStateException("You can only rate products you have purchased.")
    }

    val existingRating = productRatingRepository.findByProductIdAndUserId(productId, userId)

    val ratingToSave = if (existingRating != null) {
        existingRating.ratingValue = request.ratingValue
        existingRating.comment = request.comment
        existingRating
    } else {
        ProductRating(
            product = product,
            user = user,
            ratingValue = request.ratingValue,
            comment = request.comment
        )
    }

    productRatingRepository.save(ratingToSave)
    return MessageResponse("Rating added or updated successfully")
}

@Transactional(readOnly = true)
fun getProductRatings(productId: UUID, pageable: Pageable): PageResponse<ProductRatingResponse> {
    if (!productRepository.existsById(productId)) {
        throw ProductNotFoundException()
    }
    val currentUserId = SecurityUtils.getCurrentUserId()

    val ratingPage = if (currentUserId != null) {
        productRatingRepository.findByProductIdOrderedByUserAndDate(productId, currentUserId, pageable)
    } else {
        productRatingRepository.findByProductId(productId, pageable)
    }

    return ratingPage.toPageResponse { rating ->
        val isOwnRating = rating.user.id == currentUserId
        rating.toResponse(isOwnRating = isOwnRating)
    }
}

@Transactional(readOnly = true)
fun getProductRatingSummary(productId: UUID): ProductRatingSummaryResponse {
    if (!productRepository.existsById(productId)) {
        throw ProductNotFoundException()
    }

    val stats = productRatingRepository.getRatingStats(productId)
    val categorizedRatings = productRatingRepository.getCategorizedRatings(productId)

    val categorizedRatingsMap = mutableMapOf(
        5 to 0L,
        4 to 0L,
        3 to 0L,
        2 to 0L,
        1 to 0L
    )

    categorizedRatings.forEach { ratings ->
        categorizedRatingsMap[ratings.ratingValue] = ratings.count
    }

    val userId = SecurityUtils.getCurrentUserId()
    val canReview = userId != null && checkReviewEligibility(userId, productId)

    val userReview = if (userId != null) {
        productRatingRepository.findByProductIdAndUserId(productId, userId)?.toResponse(isOwnRating = true)
    } else {
        null
    }

    val recentReviews = productRatingRepository
        .findRecentReviewsExcludingUser(productId, userId, PageRequest.of(0, 5))
        .map { it.toResponse(isOwnRating = false) }

    return ProductRatingSummaryResponse(
        productId = productId,
        averageRating = stats.averageRating,
        totalRatings = stats.totalRatings,
        ratingCategorize = categorizedRatingsMap,
        canReview = canReview,
        userReview = userReview,
        recentReviews = recentReviews
    )
}

    private fun checkReviewEligibility(userId: UUID, productId: UUID): Boolean {
        return orderItemRepository.existsByUserIdAndProductIdAndStatusIn(
            userId,
            productId,
            listOf(OrderStatus.COMPLETED)
        )
    }

    @Transactional
    fun deleteRating(ratingId: UUID) {
        val userId = SecurityUtils.getCurrentUserId() ?: throw UserNotFoundException()
        val rating = productRatingRepository.findByIdOrNull(ratingId) 
            ?: throw NoSuchElementException("Rating not found")

        if (rating.user.id != userId) {
            throw IllegalStateException("You are not authorized to delete this rating")
        }

        productRatingRepository.delete(rating)
    }
}
