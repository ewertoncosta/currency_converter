package com.currencyconverter.service;

import com.currencyconverter.exception.InvalidCurrencyCodeException
import com.currencyconverter.model.CurrencyCodeEnum
import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeApiResponse
import com.currencyconverter.model.ExchangeTransactionDB
import com.currencyconverter.repository.ExchangeTransactionRepository
import com.currencyconverter.repository.UserRepository
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
    fun convertCurrency(request: CurrencyConversionRequest): ExchangeApiResponse {
        val currencyCodeFrom = validateCurrencyCode(request.from)
        val currencyCodeTo = validateCurrencyCode(request.to)
        val amount = validateAmount(request.amount)

        val response = exchangeApiClient.getData(request)
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
        exchangeTransactionRepository.save(transaction)

        return exchangeApiResponse
    }

    fun validateCurrencyCode(code: String): String {
        if (!CurrencyCodeEnum.isValid(code)) {
            throw InvalidCurrencyCodeException("Invalid currency code: $code")
        }
        return CurrencyCodeEnum.valueOf(code).name
    }

    fun validateAmount(amount: Double): Double {
        if (amount <= 0) {
            throw IllegalArgumentException("Amount must be greater than zero")
        }
        return amount
    }
}