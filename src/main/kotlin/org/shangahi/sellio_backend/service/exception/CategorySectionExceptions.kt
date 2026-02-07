package org.shangahi.sellio_backend.service.exception

import org.springframework.http.HttpStatus

class CategorySectionAlreadyExistException : SellioException(
    httpStatus = HttpStatus.CONFLICT,
    code = ErrorCode.CATEG_SECTION_ALREADY_EXISTS,
    message = "This Category section already exists"
)

class CategorySectionNotFoundException : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    code = ErrorCode.CATEG_NOT_FOUND,
    message = "Sorry, but this Category section was not found"
)