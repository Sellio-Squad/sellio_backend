package org.shangahi.sellio_backend.service.exception

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import jakarta.servlet.http.HttpServletRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.service.exception.ErrorCode.GEN_INTERNAL_SERVER_ERROR
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class SellioExceptionHandler(private val env: Environment) {
    private val log = LoggerFactory.getLogger(SellioExceptionHandler::class.java)

    @ExceptionHandler(SellioException::class)
    fun handleSellioException(ex: SellioException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val body = ErrorResponse(
            status = ex.httpStatus.value(),
            error = ex.httpStatus.reasonPhrase,
            message = ex.message,
            path = request.requestURI,
            code = ex.code
        )
        return ResponseEntity(body, ex.httpStatus)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val errorsMap = ex.bindingResult.fieldErrors.associate { error ->
            error.field to (error.defaultMessage ?: "Invalid value")
        }

        val generalMessage = "Validation failed for ${errorsMap.size} field(s)"

        val body = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = generalMessage,
            path = request.requestURI,
            code = ErrorCode.GEN_VALIDATION_ERROR,
            validationErrors = errorsMap
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val cause = ex.cause

        val errorMessage = when (cause) {
            is MismatchedInputException -> {
                val fieldName = cause.path.joinToString(".") { it.fieldName ?: "?" }
                "Required field '$fieldName' is missing."
            }

            is JsonMappingException -> {
                val fieldName = cause.path.joinToString(".") { it.fieldName ?: "[${it.index}]" }
                "Invalid value for field '$fieldName'."
            }

            else -> "Required request body is missing or malformed."
        }

        val body = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = errorMessage,
            path = request.requestURI,
            code = ErrorCode.GEN_REQUEST_BODY_ERROR
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("Unhandled exception occurred at path: ${request.requestURI}", ex)
        val isDev = env.activeProfiles.contains("dev")
        val message = if (isDev) {
            ex.message ?: "Unknown error"
        } else {
            "Something went wrong, please try again later."
        }
        val body = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = message,
            path = request.requestURI,
            code = GEN_INTERNAL_SERVER_ERROR
        )
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}