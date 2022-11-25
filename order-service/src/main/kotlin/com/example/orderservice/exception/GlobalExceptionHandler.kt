package com.example.orderservice.exception

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    fun handleOrderNotFoundException(ex: OrderNotFoundException): ResponseEntity<String> {
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST, ex.message)
        return ResponseEntity.ok(jacksonObjectMapper().writeValueAsString(errorResponse))
    }

    @ExceptionHandler
    fun handleWantsYourOrderInfoException(ex: WantsYourOrderInfoException): ResponseEntity<String> {
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST, ex.message)
        return ResponseEntity.ok(jacksonObjectMapper().writeValueAsString(errorResponse))
    }

    @ExceptionHandler
    fun handleWantsYourOrderIdException(ex: WantsYourOrderIdException): ResponseEntity<String> {
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST, ex.message)
        return ResponseEntity.ok(jacksonObjectMapper().writeValueAsString(errorResponse))
    }

    @ExceptionHandler
    fun handlePageNotDefinedException(exception: PageNotDefinedException): ResponseEntity<String> {
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST, exception.message)
        return ResponseEntity.ok(jacksonObjectMapper().writeValueAsString(errorResponse))
    }
}

data class ErrorResponse(
    val status: HttpStatus,
    val message: String?
)