package org.shangahi.sellio_backend.service.exception

import org.shangahi.sellio_backend.service.exception.ErrorCode.AUTH_INVALID_CREDENTIALS
import org.springframework.http.HttpStatus

class InvalidCredentialsException : SellioException (
    message = "Invalid phone number or password",
    httpStatus = HttpStatus.UNAUTHORIZED,
    code = AUTH_INVALID_CREDENTIALS
)