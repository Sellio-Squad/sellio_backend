package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.api.dto.response.RatingBreakdown
import org.shangahi.sellio_backend.api.dto.response.RatingStats
import org.shangahi.sellio_backend.entity.Store
import org.shangahi.sellio_backend.entity.StoreRating
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface StoreRatingRepository : JpaRepository<StoreRating, UUID> {


    @Query("""
        SELECT sr.store
        FROM StoreRating sr
        GROUP BY sr.store
        ORDER BY MAX(sr.ratingValue) DESC
    """)
    fun findTopStoresByHighestRating(pageable: Pageable): Page<Store>

    @Query("""
        SELECT 
            COALESCE(AVG(sr.ratingValue), 0.0) as averageRating, 
            COUNT(sr) as totalRatings
        FROM StoreRating sr
        WHERE sr.store.id = :storeId
    """)
        fun getRatingStats(@Param("storeId") storeId: UUID): RatingStats


        @Query("""
        SELECT 
            sr.ratingValue as ratingValue, 
            COUNT(sr) as count
        FROM StoreRating sr
        WHERE sr.store.id = :storeId
        GROUP BY sr.ratingValue
    """)
        fun getCategorizedRatings(@Param("storeId") storeId: UUID): List<RatingBreakdown>

}