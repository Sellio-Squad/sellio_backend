package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.OrderItem
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.repository.OrderItemRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderItemRepository: OrderItemRepository,
) {
    fun getCompletedOrdersItems(pageable: Pageable): Page<OrderItem> {
        return orderItemRepository.findAllByStatus(pageable = pageable, status = OrderStatus.COMPLETED)
    }
}