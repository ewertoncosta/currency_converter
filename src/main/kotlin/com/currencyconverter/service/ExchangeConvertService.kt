package com.currencyconverter.service;

import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeApiResponse
import com.currencyconverter.model.ExchangeTransactionDB
import com.currencyconverter.repository.ExchangeTransactionRepository
import com.currencyconverter.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ExchangeConvertService(
    private val exchangeApiClient: ExchangeRatesApi,
    private val exchangeTransactionRepository: ExchangeTransactionRepository,
    private val userRepository: UserRepository
) {
    fun convertCurrency(request: CurrencyConversionRequest): ExchangeApiResponse {
        val response = exchangeApiClient.getData(request)
        val userId = UUID.randomUUID()
        val username = userRepository.findByUsername(SecurityContextHolder.getContext().authentication.name)
            ?: throw IllegalArgumentException("User not found")

        val exchangeApiResponse = ExchangeApiResponse(
            transactionId = UUID.randomUUID(),
            userName = username.username,
            sourceCurrency = request.from,
            sourceAmount = request.amount,
            targetCurrency = response.rates.keys.first(),
            targetAmount = response.rates.values.first(),
            conversionRate = request.amount,
            utcDateTime = response.timestamp
        )

        val transaction = ExchangeTransactionDB(
            userName = username.username,
            sourceCurrency = request.from,
            sourceAmount = request.amount,
            targetCurrency = response.rates.keys.first(),
            conversionRate = response.rates.values.first(),
            utcDateTime = response.timestamp
        )
        exchangeTransactionRepository.save(transaction)

        return exchangeApiResponse
    }
}