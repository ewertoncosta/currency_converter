package com.currencyconverter.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.lang.NonNull
import java.time.LocalDate

@Schema(description = "Request model for currency conversion")
data class CurrencyConversionRequest(
    @Schema(description = "Your API Key", required = true)
    @NonNull
    val accessKey: String,

    @Schema(description = "The three-letter currency code of the currency you would like to convert from", required = true, example = "USD")
    @NonNull
    val from: String,

    @Schema(description = "The three-letter currency code of the currency you would like to convert to", required = true, example = "EUR")
    @NonNull
    val to: String,

    @Schema(description = "The amount to be converted", required = true, example = "100.0")
    @NonNull
    val amount: Double,

    @Schema(description = "Specify a date (format YYYY-MM-DD) to use historical rates for this conversion", required = false, example = "2023-01-01")
    val date: LocalDate? = null
)
