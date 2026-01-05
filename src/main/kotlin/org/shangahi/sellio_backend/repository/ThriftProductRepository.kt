package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.ThriftProduct
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ThriftProductRepository : JpaRepository<ThriftProduct, UUID>{

    @Query(
        """
            SELECT t FROM ThriftProduct t
            JOIN t.productSubCategories psc
            JOIN psc.subCategory sc
            WHERE sc.category.id = :categoryId
        
        """
    )
    fun findByCategoryId(categoryId: UUID, pageable: Pageable): Page<ThriftProduct>
}