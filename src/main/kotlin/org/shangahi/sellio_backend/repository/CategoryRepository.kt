package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<Category, UUID>{
    fun existsByTitle(name: String): Boolean
    @Query("""
        SELECT DISTINCT c FROM Category c
        JOIN c.subCategories sc
        JOIN sc.products psc
        JOIN psc.product p
        WHERE p.store.id = :storeId
    """)
    fun findCategoriesByStoreId(@Param("storeId") storeId: UUID): List<Category>
}
