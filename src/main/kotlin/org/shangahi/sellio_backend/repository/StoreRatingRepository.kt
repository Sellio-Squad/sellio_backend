package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.api.dto.RatingBreakdown
import org.shangahi.sellio_backend.api.dto.RatingStats
import org.shangahi.sellio_backend.entity.StoreRating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface StoreRatingRepository : JpaRepository<StoreRating, UUID> {


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