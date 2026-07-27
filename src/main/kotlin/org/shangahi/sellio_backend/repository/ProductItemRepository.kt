package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.ProductItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductItemRepository : JpaRepository<ProductItem, UUID> {
    fun existsByColorId(colorId: Int): Boolean
    fun existsBySizeId(sizeId: Int): Boolean
    fun existsByWeightId(sizeId: Int): Boolean

    @Query("""
        SELECT pi FROM ProductItem pi
        LEFT JOIN FETCH pi.color
        LEFT JOIN FETCH pi.size
        LEFT JOIN FETCH pi.weight
        LEFT JOIN FETCH pi.discount
        WHERE pi.product.id = :productId
    """)
    fun findAllByProductIdWithDetails(@Param("productId") productId: UUID): List<ProductItem>
}