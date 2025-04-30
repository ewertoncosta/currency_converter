package com.currencyconverter.advice

import com.currencyconverter.exception.InvalidAmountException
import com.currencyconverter.exception.InvalidCurrencyCodeException
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Hidden
@ControllerAdvice
class GlobalExceptionHandler {

     @ExceptionHandler(InvalidCurrencyCodeException::class)
     fun handleInvalidCurrencyCodeException(ex: InvalidCurrencyCodeException): ResponseEntity<String> {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
     }

    @ExceptionHandler(InvalidAmountException::class)
    fun handleInvalidAmountException(ex: InvalidAmountException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }
}