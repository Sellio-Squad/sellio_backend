package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class SizeNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.ITEM_SIZE,
    message = "Sorry, but this product Size was not found"
)

class SizeAlreadyExistException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.ITEM_SIZE,
    message = "Size value already exists"
)

class SizeInUseException : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.ITEM_SIZE,
    message = "Size is currently in use by a product and cannot be deleted"
)

