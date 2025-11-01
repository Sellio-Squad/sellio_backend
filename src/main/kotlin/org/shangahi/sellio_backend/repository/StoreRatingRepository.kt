package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Store
import org.shangahi.sellio_backend.entity.StoreRating
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StoreRatingRepository : JpaRepository<StoreRating, Long> {

    @Query("""
        SELECT sr.store
        FROM StoreRating sr
        GROUP BY sr.store
        ORDER BY MAX(sr.ratingValue) DESC
    """)
    fun findTopStoresByHighestRating(pageable: Pageable): Page<Store>


}