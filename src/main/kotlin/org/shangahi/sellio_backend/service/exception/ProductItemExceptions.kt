package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class ProductItemOutOfStockException : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.ITEM_OUT_OF_STOCK,
    message = "This productItem is currently out of stock"
)

class ProductItemNotEnoughStockException(requested: Int, available: Int) : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.ITEM_NOT_ENOUGH_STOCK,
    message = "Not enough stock. You requested $requested, but only $available are available"
)

class ProductItemNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.ITEM_NOT_FOUND,
    message = "Sorry, but this product was not found"
)

class ProductItemSavingException : SellioException(
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    code = ErrorCode.PROD_SAVING,
    message = "Sorry, Error when saving product"
)

class ProductItemInUseException: SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.ITEM_IN_USE,
    message = "this item already in use"
)