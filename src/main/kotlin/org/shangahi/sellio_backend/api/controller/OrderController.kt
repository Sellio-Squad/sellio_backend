package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.response.OrderItemResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.OrderService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderService: OrderService,
) {
    @GetMapping("/completed")
    fun getCompletedOrders(
        @ParameterObject
        @PageableDefault(page = 0, size = 20)
        pageable: Pageable
    ): PageResponse<OrderItemResponse> {
        val ordersPage = orderService.getCompletedOrdersItems(pageable)
        return ordersPage.toResponse()
    }
}