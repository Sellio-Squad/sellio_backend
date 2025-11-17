package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Discount
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Repository
interface DiscountRepository : JpaRepository<Discount, UUID> {

    fun findByStoreId(storeId: UUID, pageable: Pageable): Page<Discount>
    fun findByProductId(id: UUID, pageable: Pageable): Page<Discount>
    fun findBySubCategoryId(subCategoryId: UUID, pageable: Pageable): Page<Discount>
    fun findByCategoryId(subCategoryId: UUID, pageable: Pageable): Page<Discount>
    @Query("""
        SELECT d FROM Discount d
        WHERE d.store.id = :storeId
        AND d.product IS NULL
        AND d.category IS NULL
        AND d.subCategory IS NULL
        AND (d.endDate IS NULL OR d.endDate >= :now)
    """)
    fun findActiveStoreDiscounts(
        @Param("storeId") storeId: UUID,
        @Param("now") now: Instant = Instant.now()
    ): List<Discount>
}
