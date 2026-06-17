package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class ProductNotPurchasedException : SellioException(
    httpStatus = HttpStatus.FORBIDDEN,
    code = ErrorCode.RATING_NOT_PURCHASED,
    message = "You can only rate products you have purchased."
)

class RatingNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.RATING_NOT_FOUND,
    message = "Rating not found."
)

class UnauthorizedRatingDeletionException : SellioException(
    httpStatus = HttpStatus.FORBIDDEN,
    code = ErrorCode.RATING_NOT_OWNER,
    message = "You are not authorized to delete this rating."
)
