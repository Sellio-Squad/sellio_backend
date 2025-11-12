package org.shangahi.sellio_backend.service.exception

import org.shangahi.sellio_backend.service.exception.ErrorCode.AUTH_INVALID_CREDENTIALS
import org.shangahi.sellio_backend.service.exception.ErrorCode.AUTH_INVALID_REFRESH_TOKEN
import org.shangahi.sellio_backend.service.exception.ErrorCode.AUTH_UNAUTHORIZED
import org.shangahi.sellio_backend.service.exception.ErrorCode.INVALID_PHONE_NUMBER
import org.shangahi.sellio_backend.service.exception.ErrorCode.OTP_EXPIRED
import org.shangahi.sellio_backend.service.exception.ErrorCode.OTP_INVALID
import org.springframework.http.HttpStatus

class InvalidCredentialsException : SellioException (
    message = "Invalid phone number or password",
    httpStatus = HttpStatus.UNAUTHORIZED,
    code = AUTH_INVALID_CREDENTIALS
)

class InvalidRefreshTokenException : SellioException (
    message = "Invalid refresh token",
    httpStatus = HttpStatus.UNAUTHORIZED,
    code = AUTH_INVALID_REFRESH_TOKEN
)

class InvalidPhoneNumberException : SellioException (
    message = "Invalid phone number",
    httpStatus = HttpStatus.BAD_REQUEST,
    code = INVALID_PHONE_NUMBER
)

class UnauthorizedException : SellioException (
    message = "Unauthorized",
    httpStatus = HttpStatus.UNAUTHORIZED,
    code = AUTH_UNAUTHORIZED
)

class InvalidOtpException : SellioException (
    message = "Invalid OTP",
    httpStatus = HttpStatus.BAD_REQUEST,
    code = OTP_INVALID
)

class OtpExpiredException : SellioException (
    message = "OTP expired",
    httpStatus = HttpStatus.BAD_REQUEST,
    code = OTP_EXPIRED
)

