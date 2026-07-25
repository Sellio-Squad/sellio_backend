package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Orders
import org.shangahi.sellio_backend.model.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Orders, UUID> {
    fun findAllByUserId(userId: UUID, pageable: Pageable): Page<Orders>
    fun findAllByUserIdAndStatus(userId: UUID, status: OrderStatus, pageable: Pageable): Page<Orders>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Orders o WHERE o.id = :id")
    fun findByIdWithLock(@Param("id") id: UUID): Orders?
}