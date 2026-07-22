package org.shangahi.sellio_backend.service.exception

import org.shangahi.sellio_backend.model.OrderStatus
import org.springframework.http.HttpStatus

class OrderNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.ORDER_NOT_FOUND,
    message = "Order not found"
)

class OrderCannotBeCancelledException(currentStatus: OrderStatus) : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.ORDER_CANNOT_CANCEL,
    message = "Order cannot be cancelled in its current status: $currentStatus"
)
