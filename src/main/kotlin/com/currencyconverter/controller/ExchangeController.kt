package com.currencyconverter.controller

import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeApiResponse
import com.currencyconverter.model.ExchangeTransactionDB
import com.currencyconverter.repository.ExchangeTransactionRepository
import com.currencyconverter.service.ExchangeConvertService
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Schema(description = "Controller for handling currency exchange operations")
class ExchangeController(
    private val exchangeConvertService: ExchangeConvertService,
    private val exchangeTransactionRepository: ExchangeTransactionRepository
) {
    @Schema(description = "Endpoint to convert currency")
    @GetMapping("/exchange", produces = ["application/json"])
    fun getExchangeRate(
        @Schema(description = "The three-letter currency code of the currency you would like to convert from", required = true, example = "USD")
        @RequestParam("from") from: String,
        @Schema(description = "The three-letter currency code of the currency you would like to convert to", required = true, example = "EUR")
        @RequestParam("to") to: String,
        @Schema(description = "The amount to be converted", required = true, example = "100.0")
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

    @Schema(description = "Endpoint to get exchange rate conversion history")
    @GetMapping("/exchange/history", produces = ["application/json"])
    fun getExchangeRateHistory(
        @Schema(description = "The username of the user whose transaction history is to be retrieved", required = true, example = "testUser")
        @RequestParam("userName") userName: String,
    ): List<ExchangeTransactionDB> {
        val response = exchangeTransactionRepository.findByUserName(userName)
        return response
    }
}