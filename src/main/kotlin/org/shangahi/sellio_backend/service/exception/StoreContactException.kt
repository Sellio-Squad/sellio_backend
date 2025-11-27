package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class StoreContactTypeAlreadyExistsException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.STORE_CONTACT_TYPE_ALREADY_EXISTS,
    message = "Store contact type already exists"
)

class StoreContactValueAlreadyExistsException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.STORE_CONTACT_VALUE_ALREADY_EXISTS,
    message = "Store contact value already exists"
)

class StoreContactNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.STORE_CONTACT_NOT_FOUND,
    message = "Store contact not found"
)
