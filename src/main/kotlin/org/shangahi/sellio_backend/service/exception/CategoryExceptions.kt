package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class CategoryAlreadyExistException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.CATEG_ALREADY_EXISTS,
    message = "This Category already exists"
)

class CategoryNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.CATEG_NOT_FOUND,
    message = "Sorry, but this Category was not found"
)