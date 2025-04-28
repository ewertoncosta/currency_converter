package com.currencyconverter.model
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Details of the query")
data class QueryDetails(
    @Schema(description = "The three-letter currency code of the currency converted from", required = true, example = "USD")
    val from: String,

    @Schema(description = "The three-letter currency code of the currency converted to", required = true, example = "EUR")
    val to: String,

    @Schema(description = "The amount that is converted", required = true, example = "100.0")
    val amount: Double
)
