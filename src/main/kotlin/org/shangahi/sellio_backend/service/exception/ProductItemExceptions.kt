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

class ProductItemColorNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.ITEM_COLOR,
    message = "Sorry, but this product Color was not found"
)

class ProductItemSizeNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.ITEM_SIZE,
    message = "Sorry, but this product Size was not found"
)

class ProductItemWeightNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.ITEM_WEIGHT,
    message = "Sorry, but this product Weight was not found"
)