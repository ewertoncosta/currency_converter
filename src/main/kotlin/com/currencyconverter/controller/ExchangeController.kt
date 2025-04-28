package com.currencyconverter.controller

import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeApiResponse
import com.currencyconverter.model.ExchangeTransactionDB
import com.currencyconverter.repository.ExchangeTransactionRepository
import com.currencyconverter.service.ExchangeConvertService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.UUID

@RestController
class ExchangeController(
    private val exchangeConvertService: ExchangeConvertService,
    private val exchangeTransactionRepository: ExchangeTransactionRepository
) {
    @GetMapping("/exchange", produces = ["application/json"])
    fun getExchangeRate(
        @RequestParam("from") from: String,
        @RequestParam("to") to: String,
        @RequestParam("amount") amount: Double
    ): ExchangeApiResponse {
        val response = exchangeConvertService.convertCurrency(
            CurrencyConversionRequest(
                from = from,
                to = to,
                amount = amount
            )
        )
        return response
    }
    @GetMapping("/exchange/history", produces = ["application/json"])
    fun getExchangeRateHistory(
        @RequestParam("userId") userId: UUID,
    ): List<ExchangeTransactionDB> {
        val response = exchangeTransactionRepository.findByUserId(userId)
        return response
    }
}