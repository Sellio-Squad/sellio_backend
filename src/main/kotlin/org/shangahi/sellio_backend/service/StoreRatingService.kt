package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.StoreRatingResponse
import org.shangahi.sellio_backend.repository.StoreRatingRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.shangahi.sellio_backend.service.exception.StoreNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.collections.forEach


@Service
class StoreRatingService(
    private val storeRatingRepository: StoreRatingRepository,
    private val storeRepository: StoreRepository
) {
    fun getStoreRatingSummary(storeId: UUID): StoreRatingResponse {

        if (!storeRepository.existsById(storeId)) {
            throw StoreNotFoundException()
        }
        val stats = storeRatingRepository.getRatingStats(storeId)
        val categorizedRatings = storeRatingRepository.getCategorizedRatings(storeId)
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

        return StoreRatingResponse(
            id = storeId,
            averageRating = stats.averageRating,
            totalRatings = stats.totalRatings,
            ratingCategorize =  categorizedRatingsMap,
        )
    }

}