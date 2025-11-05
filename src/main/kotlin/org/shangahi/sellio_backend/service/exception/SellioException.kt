package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

open class SellioException (
    val httpStatus: HttpStatus,
    val code: String,
    override val message: String
): Exception(message)


class ValidationException(message: String) : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.GEN_VALIDATION_ERROR,
    message = message
)

class RequestBodyException : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.GEN_REQUEST_BODY_ERROR,
    message = "Request body is missing"
)

class InternalServerErrorException(details:String) : SellioException(
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    code = ErrorCode.GEN_INTERNAL_SERVER_ERROR,
    message = "An unexpected internal error: $details"
)