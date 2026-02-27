package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.ProductItem
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.model.TrendingProduct
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductItemRepository : JpaRepository<ProductItem, UUID> {
    @Query(
        """
        SELECT new org.shangahi.sellio_backend.model.TrendingProduct(
            p.id,
            p.title,
            p.description,
            p.store.id,
            SUM(oi.quantity), 
            MIN(pi.price),           
            p.mainImageURL
        )
        FROM OrderItem oi
        JOIN oi.productItem pi
        JOIN pi.product p
        WHERE oi.status = :status
        GROUP BY p.id
        ORDER BY SUM(oi.quantity) DESC
    """
    )
    fun findTrendingProducts(
        @Param("status") status: OrderStatus,
        pageable: Pageable
    ): Page<TrendingProduct>

    @Query(
        """
    SELECT new org.shangahi.sellio_backend.model.TrendingProduct(
        p.id,
        p.title,
        p.description,
        p.store.id,
        0L,
        (
            SELECT MIN(pi.price)
            FROM ProductItem pi
            WHERE pi.product.id = p.id
        ),
        p.mainImageURL
    )
    FROM Product p
    ORDER BY p.createdAt DESC
    """
    )
    fun findAllProducts(pageable: Pageable): Page<TrendingProduct>
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