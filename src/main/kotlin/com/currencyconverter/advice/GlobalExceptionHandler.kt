package com.currencyconverter.advice

import com.currencyconverter.exception.InvalidCurrencyCodeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

     @ExceptionHandler(InvalidCurrencyCodeException::class)
     fun handleInvalidCurrencyCodeException(ex: InvalidCurrencyCodeException): ResponseEntity<String> {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
     }
}