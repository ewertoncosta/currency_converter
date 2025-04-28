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