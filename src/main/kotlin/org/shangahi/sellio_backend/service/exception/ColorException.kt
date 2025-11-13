package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class ColorNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.ITEM_COLOR,
    message = "Sorry, but this product Color was not found"
)

class ColorAlreadyExistException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.ITEM_COLOR,
    message = "Color value already exists"
)

class ColorInUseException : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.ITEM_COLOR,
    message = "Color is currently in use by a product and cannot be deleted"
)

