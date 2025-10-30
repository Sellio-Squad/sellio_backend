package org.shangahi.sellio_backend.service.exception

import jakarta.servlet.http.HttpServletRequest
import org.shangahi.sellio_backend.api.dto.ErrorResponse
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SellioExceptionHandler {

    @ExceptionHandler(SellioException::class)
    fun handleSellioException(ex: SellioException, req: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = ex.httpStatus.value(),
            error = ex.httpStatus.reasonPhrase,
            message = ex.message,
            path = req.requestURI,
            code = ex.code
        )
        return ResponseEntity(body, ex.httpStatus)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException, req: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        val body = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = errors,
            path = req.requestURI,
            code = "VALIDATION_ERROR"
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleUnreadable(ex: HttpMessageNotReadableException, req: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "Malformed JSON request: ${ex.cause?.message ?: ex.message}",
            path = req.requestURI,
            code = "MALFORMED_JSON"
        )
        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrity(
        ex: DataIntegrityViolationException,
        req: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            error = HttpStatus.CONFLICT.reasonPhrase,
            message = "Database constraint violation",
            path = req.requestURI,
            code = "DB_CONSTRAINT_VIOLATION"
        )
        return ResponseEntity(body, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, req: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = "Unexpected server error",
            path = req.requestURI,
            code = "INTERNAL_ERROR"
        )
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
