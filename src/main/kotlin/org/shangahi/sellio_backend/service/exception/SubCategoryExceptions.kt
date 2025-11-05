package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class SubCategoryAlreadyExistException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.SUBCATEG_ALREADY_EXISTS,
    message = "This Subcategory already exists"
)

class SubCategoryNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.SUBCATEG_NOT_FOUND,
    message = "Sorry, but this Subcategory was not found"
)