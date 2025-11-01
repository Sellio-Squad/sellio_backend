package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.OrderItemResponse
import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.OrderService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService,
) {
    @GetMapping("/completed")
    fun getCompletedOrders(pageable: Pageable): PageResponse<OrderItemResponse> {
        val ordersPage = orderService.getCompletedOrdersItems(pageable)
        return ordersPage.toResponse()
    }
}