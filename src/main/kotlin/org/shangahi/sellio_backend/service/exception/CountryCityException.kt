package org.shangahi.sellio_backend.service.exception

import org.shangahi.sellio_backend.service.exception.ErrorCode.CITIES_NOT_FOUND
import org.springframework.http.HttpStatus

class CitiesNotFoundException(val causeMessage: String? = null) : SellioException(
    httpStatus = HttpStatus.NOT_FOUND,
    message = "Cities Not Found!\nCause: $causeMessage",
    code = CITIES_NOT_FOUND
)