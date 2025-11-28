package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class StoreContactTypeAlreadyExistsException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.STORE_CONTACT_TYPE_ALREADY_EXISTS,
    message = "Store contact type already exists"
)

class StoreContactTypeDuplicateException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.STORE_CONTACT_TYPE_DUPLICATE,
    message = "Store contact type duplicate in your request"
)

class StoreContactNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.STORE_CONTACT_NOT_FOUND,
    message = "Store contact not found"
)
