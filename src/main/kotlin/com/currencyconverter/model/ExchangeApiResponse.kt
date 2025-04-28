package com.currencyconverter.model

import io.swagger.v3.oas.annotations.media.Schema
import org.jetbrains.annotations.NotNull
import java.util.*

@Schema(description = "Response model for exchange API")
data class ExchangeApiResponse(
    @Schema(description = "The unique identifier for the transaction", required = true, example = "123456789")
    val transactionId: UUID,

    @Schema(description = "The unique identifier for the user", required = true, example = "987654321")
    val userName: String? = "",

    @Schema(description = "The three-letter currency code of the currency converted from", required = true, example = "USD")
    val sourceCurrency: String = "",

    @Schema(description = "The amount that is converted", required = true, example = "100.0")
    val sourceAmount: Double,

    @Schema(description = "The three-letter currency code of the currency converted to", required = true, example = "EUR")
    val targetCurrency: String = "",

    @Schema(description = "The amount that is converted to", required = true, example = "85.0")
    val targetAmount: Double,

    @Schema(description = "The exchange rate used for the conversion", required = true, example = "0.85")
    @NotNull
    val conversionRate: Double,

    @Schema(description = "The exact date and time (UNIX time stamp) the given exchange rate was collected", required = true, example = "1672531200")
    val utcDateTime: Long,
)
