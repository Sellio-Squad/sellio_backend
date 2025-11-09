package org.shangahi.sellio_backend.service.exception

import org.shangahi.sellio_backend.service.exception.ErrorCode.IMAGE_INVALID_FORMAT
import org.shangahi.sellio_backend.service.exception.ErrorCode.IMAGE_UPLOAD_FAILED
import org.springframework.http.HttpStatus

class InvalidImageFormatException : SellioException(
    code = IMAGE_INVALID_FORMAT,
    httpStatus = HttpStatus.BAD_REQUEST,
    message = "Invalid picture format"
)

class ImageUploadFailedException : SellioException(
    code = IMAGE_UPLOAD_FAILED,
    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    message = "Image uploading failed"
)
