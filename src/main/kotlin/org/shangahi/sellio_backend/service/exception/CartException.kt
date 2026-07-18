package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class CartNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.CART_NOT_FOUND,
    message = "Cart not found"
)

class CartItemNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.CART_ITEM_NOT_FOUND,
    message = "Cart item not found"
)

class CartItemQuantityExceedsStockException(stock: Int) : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.CART_QUANTITY_EXCEEDS_STOCK,
    message = "Requested quantity exceeds available stock of $stock"
)

class CartItemInvalidQuantityException : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.CART_INVALID_QUANTITY,
    message = "Quantity must be greater than zero"
)

class CartIsEmptyException : SellioException(
    httpStatus = HttpStatus.BAD_REQUEST,
    code = ErrorCode.CART_IS_EMPTY,
    message = "Cart is empty. Add items before placing an order"
)
