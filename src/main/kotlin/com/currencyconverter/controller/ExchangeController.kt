package com.currencyconverter.controller

import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeApiResponse
import com.currencyconverter.model.ExchangeTransactionDB
import com.currencyconverter.repository.ExchangeTransactionRepository
import com.currencyconverter.service.ExchangeConvertService
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Currency Exchange", description = "API endpoints for currency conversion operations")
@SecurityRequirement(name = "basicAuth")
@ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "Successful operation"),
    ApiResponse(responseCode = "400", description = "Invalid request parameters"),
    ApiResponse(responseCode = "401", description = "Unauthorized"),
    ApiResponse(responseCode = "500", description = "Internal server error")
])
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
        @RequestParam("amount") amount: Double,
        @Schema(description = "Your API Key", required = true)
        @RequestParam("accessKey") accessKey: String
    ): ExchangeApiResponse {
        val response = exchangeConvertService.convertCurrency(
            CurrencyConversionRequest(
                from = from,
                to = to,
                amount = amount,
                accessKey = accessKey
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