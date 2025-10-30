package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

open class SellioException (
    val httpStatus: HttpStatus,
    val code: String,
    override val message: String
):RuntimeException(message)

class NotFoundException(code: String, message: String): SellioException(HttpStatus.NOT_FOUND, code, message)
class BadRequestException(code: String, message: String): SellioException(HttpStatus.BAD_REQUEST, code, message)
class UnauthorizedException(code: String, message: String): SellioException(HttpStatus.UNAUTHORIZED, code, message)
class ConflictException(code: String, message: String): SellioException(HttpStatus.CONFLICT, code, message)