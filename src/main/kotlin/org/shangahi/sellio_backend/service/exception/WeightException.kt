package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class WeightNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.ITEM_WEIGHT,
    message = "Sorry, but this product Weight was not found"
)

class WeightAlreadyExistException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.ITEM_WEIGHT,
    message = "Weight value already exists"
)

class WeightInUseException : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.ITEM_WEIGHT,
    message = "Weight is currently in use by a product and cannot be deleted"
)