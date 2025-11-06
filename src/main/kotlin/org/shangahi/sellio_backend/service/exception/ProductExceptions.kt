package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class ProductOutOfStockException : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.PROD_OUT_OF_STOCK,
    message = "This product is currently out of stock"
)

class ProductNotEnoughStockException(requested: Int, available: Int) : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.PROD_NOT_ENOUGH_STOCK,
    message = "Not enough stock. You requested $requested, but only $available are available"
)

class ProductNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.PROD_NOT_FOUND,
    message = "Sorry, but this product was not found"
)

class ProductSavingException : SellioException(
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    code = ErrorCode.PROD_SAVING,
    message = "Sorry, Error when saving product"
)