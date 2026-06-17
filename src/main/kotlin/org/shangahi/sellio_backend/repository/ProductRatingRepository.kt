package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.api.dto.response.RatingBreakdown
import org.shangahi.sellio_backend.api.dto.response.RatingStats
import org.shangahi.sellio_backend.entity.ProductRating
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductRatingRepository : JpaRepository<ProductRating, UUID> {
    fun findByProductId(productId: UUID, pageable: Pageable): Page<ProductRating>

    @Query("""
        SELECT pr FROM ProductRating pr 
        WHERE pr.product.id = :productId 
        ORDER BY 
          CASE WHEN pr.user.id = :userId THEN 0 ELSE 1 END ASC, 
          pr.createdAt DESC
    """)
    fun findByProductIdOrderedByUserAndDate(
        @Param("productId") productId: UUID, 
        @Param("userId") userId: UUID, 
        pageable: Pageable
    ): Page<ProductRating>

    @Query("""
        SELECT pr FROM ProductRating pr 
        WHERE pr.product.id = :productId 
        AND (:userId IS NULL OR pr.user.id != :userId)
        ORDER BY pr.createdAt DESC
    """)
    fun findRecentReviewsExcludingUser(
        @Param("productId") productId: UUID, 
        @Param("userId") userId: UUID?, 
        pageable: Pageable
    ): List<ProductRating>

    fun findByUserId(userId: UUID): List<ProductRating>
    fun findByProductIdAndUserId(productId: UUID, userId: UUID): ProductRating?

    @Query("""
        SELECT 
            COALESCE(AVG(pr.ratingValue), 0.0) as averageRating, 
            COUNT(pr) as totalRatings
        FROM ProductRating pr
        WHERE pr.product.id = :productId
    """)
    fun getRatingStats(@Param("productId") productId: UUID): RatingStats

    @Query("""
        SELECT 
            pr.ratingValue as ratingValue, 
            COUNT(pr) as count
        FROM ProductRating pr
        WHERE pr.product.id = :productId
        GROUP BY pr.ratingValue
    """)
    fun getCategorizedRatings(@Param("productId") productId: UUID): List<RatingBreakdown>
}
