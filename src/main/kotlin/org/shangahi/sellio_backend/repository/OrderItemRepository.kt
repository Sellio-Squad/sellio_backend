package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.OrderItem
import org.shangahi.sellio_backend.model.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderItemRepository : JpaRepository<OrderItem, UUID> {

    fun findAllByStatus(status: OrderStatus, pageable: Pageable): Page<OrderItem>

}
