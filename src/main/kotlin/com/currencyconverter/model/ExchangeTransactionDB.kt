package com.currencyconverter.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Schema(description = "Response model for exchange API that is saved on the database")
@Entity
data class ExchangeTransactionDB(
    @Schema(description = "The unique identifier for the transaction", required = true, example = "123456789")
    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    @Schema(description = "The unique identifier for the user", required = true, example = "123456789")
    @NotNull
    val userId: UUID = UUID.randomUUID(),

    @Schema(description = "The unique identifier for the user", required = true, example = "987654321")
    @NotNull
    val sourceCurrency: String = "",

    @Schema(description = "The amount that is converted", required = true, example = "100.0")
    @NotNull
    val sourceAmount: Double = 0.0,

    @Schema(description = "The three-letter currency code of the currency converted to", required = true, example = "EUR")
    @NotNull
    val targetCurrency: String = "",

    @Schema(description = "The amount that is converted to", required = true, example = "85.0")
    @NotNull
    val conversionRate: Double = 0.0,

    @Schema(description = "The exchange rate used for the conversion", required = true, example = "0.85")
    @NotNull
    val utcDateTime: Long = LocalDateTime.now(ZoneOffset.UTC).toEpochSecond(ZoneOffset.UTC) * 1000L,
)