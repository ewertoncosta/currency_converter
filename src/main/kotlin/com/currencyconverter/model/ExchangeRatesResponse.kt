package com.currencyconverter.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "Response model for exchange rates")
data class ExchangeRatesResponse(
    @Schema(description = "Indicates whether the API request was successful", required = true)
    val success: Boolean,

    @Schema(description = "The exact date and time (UNIX timestamp) the exchange rates were collected", required = true, example = "1745792944")
    val timestamp: Long,

    @Schema(description = "The base currency for the exchange rates", required = true, example = "EUR")
    val base: String,

    @Schema(description = "The date the exchange rates were collected", required = true, example = "2025-04-27")
    val date: LocalDate,

    @Schema(description = "A map of currency codes to their exchange rates", required = true)
    val rates: Map<String, Double>
)