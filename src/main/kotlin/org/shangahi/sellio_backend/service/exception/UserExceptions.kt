package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus


class UserEmailAlreadyExistsException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.USER_EMAIL_ALREADY_EXISTS,
    message = "This email is already registered"
)

class UserPhoneNumberAlreadyExistsException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.USER_PHONE_NUMBER_ALREADY_EXISTS,
    message = "This phone number is already registered"
)

class UserCountyNotSupportedException() : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.USER_COUNTY_NOT_SUPPORTED,
    message = "Sorry, we do not support provided country at the moment"
)

class UserNotFoundException() : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.USER_NOT_FOUND,
    message = "User with provided ID was not found"
)