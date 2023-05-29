package com.example.validation.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(value = [MethodArgumentNotValidException::class, ConstraintViolationException::class, MethodArgumentTypeMismatchException::class, HttpMessageNotReadableException::class])
    fun handleMethodArgumentNotValidException(exception: Exception): ResponseEntity<ErrorResponse> {
        val message = exception
            .suppressed
            .map { error -> error.message }
            .joinToString(", ")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(message))
    }
}

data class ErrorResponse(
    val message: String
)
