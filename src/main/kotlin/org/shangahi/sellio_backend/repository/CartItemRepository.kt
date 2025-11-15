package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartItemRepository : JpaRepository<OrderItem, UUID> {

    fun existsByProductItemId(productItemId: UUID): Boolean
}
