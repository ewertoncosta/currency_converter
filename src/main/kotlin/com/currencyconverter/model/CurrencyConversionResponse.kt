package com.currencyconverter.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "Response model for currency conversion")
data class CurrencyConversionResponse(
    @Schema(description = "Returns true or false depending on whether or not your API request has succeeded", required = true)
    val success: Boolean,

    @Schema(description = "Details of the query")
    val query: QueryDetails,

    @Schema(description = "Information about the exchange rate")
    val info: ExchangeInfo,

    @Schema(description = "Returns true if historical rates are used for this conversion", required = true)
    val historical: Boolean,

    @Schema(description = "Returns the date (format YYYY-MM-DD) the given exchange rate data was collected", required = true, example = "2023-01-01")
    val date: LocalDate,

    @Schema(description = "Returns your conversion result", required = true, example = "85.0")
    val result: Double
)

@Schema(description = "Details of the query")
data class QueryDetails(
    @Schema(description = "The three-letter currency code of the currency converted from", required = true, example = "USD")
    val from: String,

    @Schema(description = "The three-letter currency code of the currency converted to", required = true, example = "EUR")
    val to: String,

    @Schema(description = "The amount that is converted", required = true, example = "100.0")
    val amount: Double
)

@Schema(description = "Information about the exchange rate")
data class ExchangeInfo(
    @Schema(description = "The exact date and time (UNIX time stamp) the given exchange rate was collected", required = true, example = "1672531200")
    val timestamp: Long,

    @Schema(description = "The exchange rate used for your conversion", required = true, example = "0.85")
    val rate: Double
)
