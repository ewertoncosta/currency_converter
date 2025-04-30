package com.currencyconverter.service;

import com.currencyconverter.exception.InvalidCurrencyCodeException
import com.currencyconverter.model.CurrencyCodeEnum
import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeApiResponse
import com.currencyconverter.model.ExchangeTransactionDB
import com.currencyconverter.repository.ExchangeTransactionRepository
import com.currencyconverter.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional

class ExchangeConvertService(
    private val exchangeApiClient: ExchangeRatesApiService,
    private val exchangeTransactionRepository: ExchangeTransactionRepository,
    private val userRepository: UserRepository
) {
    private val logger = LoggerFactory.getLogger(ExchangeConvertService::class.java)

    fun convertCurrency(request: CurrencyConversionRequest): ExchangeApiResponse {
        logger.info("Converting currency from ${request.from} to ${request.to} with amount ${request.amount}")
        val currencyCodeFrom = validateCurrencyCode(request.from)
        val currencyCodeTo = validateCurrencyCode(request.to)
        val amount = validateAmount(request.amount)

        val response = exchangeApiClient.getData(request)
        logger.info("Received response from API.")
        if (response.rates.isNotEmpty()) {
            logger.info("Rates found for the given currencies.")
            val username = userRepository.findByUsername(SecurityContextHolder.getContext().authentication.name)
                ?: throw IllegalArgumentException("User not found")

            val exchangeApiResponse = ExchangeApiResponse(
                transactionId = UUID.randomUUID(),
                userName = username.username,
                sourceCurrency = currencyCodeFrom,
                sourceAmount = amount,
                targetCurrency = currencyCodeTo,
                targetAmount = response.rates.values.first(),
                conversionRate = request.amount,
                utcDateTime = response.timestamp
            )

            val transaction = ExchangeTransactionDB(
                userName = username.username,
                sourceCurrency = currencyCodeFrom,
                sourceAmount = amount,
                targetCurrency = currencyCodeTo,
                conversionRate = response.rates.values.first(),
                utcDateTime = response.timestamp
            )
            logger.info("Saving transaction to the database.")
            exchangeTransactionRepository.save(transaction)
            logger.info("Transaction saved to the database.")

            return exchangeApiResponse
        }
        throw IllegalArgumentException("No rates found for the given currencies")
    }

    fun validateCurrencyCode(code: String): String {
        if (!CurrencyCodeEnum.isValid(code)) {
            throw InvalidCurrencyCodeException("Invalid currency code: $code")
        }
        return CurrencyCodeEnum.valueOf(code).name
    }

    fun validateAmount(amount: Double): Double {
        require(amount > 0) { "Amount must be greater than zero" }
        return amount
    }
}