package com.currencyconverter.model

import java.time.LocalDateTime
import java.time.ZoneOffset

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response model for exchange API that is saved on the database")
data class ExchangeTransaction(
    @Schema(description = "The unique identifier for the transaction", required = true, example = "123456789")
    val userId: Long,

    @Schema(description = "The unique identifier for the user", required = true, example = "987654321")
    val sourceCurrency: String,

    @Schema(description = "The amount that is converted", required = true, example = "100.0")
    val sourceAmount: Double,

    @Schema(description = "The three-letter currency code of the currency converted to", required = true, example = "EUR")
    val targetCurrency: String,

    @Schema(description = "The amount that is converted to", required = true, example = "85.0")
    val conversionRate: Double,

    @Schema(description = "The exchange rate used for the conversion", required = true, example = "0.85")
    val utcDateTime: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC),
)
