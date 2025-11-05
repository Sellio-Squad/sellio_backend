package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class StoreEmailExistException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.STORE_EMAIL_ALREADY_EXISTS,
    message = "Store email already exists, you should try another one"
)
class StorePhoneNumberExistException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.STORE_PHONE_NUMBER_ALREADY_EXIST,
    message = "Store phone number already exists, you should try another one"
)
class StoreNameExistException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.STORE_NAME_ALREADY_EXISTS,
    message = "Please try different name, Store name already exists"
)
class StoreNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.STORE_NOT_FOUND,
    message = "sorry, but store not found"
)
class StoreInActiveException : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.STORE_INACTIVE,
    message = "Unfortunately this store is inactive"
)
class StoreNotOwnerException : SellioException(
    httpStatus = HttpStatus.FORBIDDEN,
    code = ErrorCode.STORE_NOT_OWNER,
    message = "Not an owner of this store"
)

class StoreAlreadyFavoriteException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.STORE_ALREADY_FAVORITE,
    message = "Store already favorited by this user"
)
