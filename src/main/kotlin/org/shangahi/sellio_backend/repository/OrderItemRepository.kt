package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.OrderItem
import org.shangahi.sellio_backend.model.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, UUID> {

    fun findAllByStatus(status: OrderStatus, pageable: Pageable): Page<OrderItem>
    fun existsByProductItemId(productItemId: UUID): Boolean

        @Query("""
        SELECT oi FROM OrderItem oi
        JOIN FETCH oi.productItem pi
        JOIN FETCH pi.product p
        WHERE oi.order.id IN :orderIds
    """)
    fun findAllByOrderId(@Param("orderIds") orderIds: List<UUID>): List<OrderItem>

    @Query("""
        SELECT COUNT(oi) > 0 
        FROM OrderItem oi 
        WHERE oi.order.user.id = :userId 
        AND oi.productItem.product.id = :productId 
        AND oi.order.status IN :statuses
    """)
    fun existsByUserIdAndProductIdAndStatusIn(
        @Param("userId") userId: UUID,
        @Param("productId") productId: UUID,
        @Param("statuses") statuses: List<OrderStatus>
    ): Boolean
}
