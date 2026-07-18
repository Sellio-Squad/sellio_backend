package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.request.ConfirmOrderRequest
import org.shangahi.sellio_backend.api.dto.response.ConfirmOrderResponse
import org.shangahi.sellio_backend.api.dto.response.OrderHistoryResponse
import org.shangahi.sellio_backend.api.dto.response.OrderItemResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.mapper.OrderHistoryResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.doc.OrderDoc
import org.shangahi.sellio_backend.model.OrderStatus
import org.shangahi.sellio_backend.service.OrderService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("v1/orders")
@Tag(name = "Orders", description = "Endpoints for managing orders")
class OrderController(
    private val orderService: OrderService,
) {
    @OrderDoc.GetCompletedOrders
    @GetMapping("/completed")
    fun getCompletedOrders(
        @ParameterObject
        @PageableDefault(page = 0, size = 20)
        pageable: Pageable
    ): PageResponse<OrderItemResponse> {
        val ordersPage = orderService.getCompletedOrdersItems(pageable)
        return ordersPage.toPageResponse { it.toResponse() }
    }

    @OrderDoc.ConfirmOrder
    @PostMapping("/confirm")
    fun confirmOrder(
        @RequestBody(required = false) request: ConfirmOrderRequest?,
        @AuthenticationPrincipal
        userId: UUID,
    ): ResponseEntity<ConfirmOrderResponse> {

        val response = ConfirmOrderResponse(
            message = "Orders placed successfully",
            orderIds = orderService.confirmOrder(userId, request?.note)
        )
        return ResponseEntity.ok(response)
    }

    @OrderDoc.GetOrderHistory
    @GetMapping("/history")
    fun getUserOrders(
        @ParameterObject
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC)
        pageable: Pageable,
        @RequestParam(required = false)
        status: OrderStatus?,
        @AuthenticationPrincipal
        userId: UUID,
    ): PageResponse<OrderHistoryResponse> {
        val orders = orderService.getOrders(userId, status, pageable)
        val itemsGroupedByOrder = orderService.getOrderItemsGroupedByOrder(orders)
        return orders.toPageResponse {
            val orderItems = itemsGroupedByOrder[it.id] ?: emptyList()
            it.OrderHistoryResponse(orderItems)
        }
    }
}
