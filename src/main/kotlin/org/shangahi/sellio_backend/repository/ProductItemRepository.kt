package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.ProductItem
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.model.TrendingProduct
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ProductItemRepository : JpaRepository<ProductItem, UUID> {
    @Query(
        """
        SELECT new org.shangahi.sellio_backend.model.TrendingProduct(
            p.id,
            p.title,
            p.description,
            p.store.id,
            SUM(oi.quantity)
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
        0L
    )
    FROM Product p
    ORDER BY p.createdAt DESC
    """
    )
    fun findAllProducts(pageable: Pageable): Page<TrendingProduct>
}