package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.SubCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface SubCategoryRepository : JpaRepository<SubCategory, UUID> {
    fun findByCategoryId(categoryId: UUID): List<SubCategory>

    @Query(
        """
    SELECT DISTINCT sc
    FROM SubCategory sc
    JOIN sc.products ps
    JOIN ps.product p
    WHERE p.store.id = :storeId
    """
    )
    fun findAllByStoreId(@Param("storeId") storeId: UUID): List<SubCategory>
    fun existsByTitle(@Param("title") title: String): Boolean
    fun findAllByCategoryIdIn(categoryIds: List<UUID>): List<SubCategory>

}