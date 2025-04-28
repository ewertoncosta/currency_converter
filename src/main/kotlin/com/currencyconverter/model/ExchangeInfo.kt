package com.currencyconverter.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Information about the exchange rate")
data class ExchangeInfo(
    @Schema(description = "The exact date and time (UNIX time stamp) the given exchange rate was collected", required = true, example = "1672531200")
    val timestamp: Long,

    @Schema(description = "The exchange rate used for your conversion", required = true, example = "0.85")
    val rate: Double
)
