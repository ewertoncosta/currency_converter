package com.currencyconverter.service;

import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeApiResponse
import com.currencyconverter.model.ExchangeTransactionDB
import com.currencyconverter.repository.ExchangeTransactionRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ExchangeConvertService(
    private val exchangeApiClient: ExchangeRatesApi,
    private val exchangeTransactionRepository: ExchangeTransactionRepository
) {
    fun convertCurrency(request: CurrencyConversionRequest): ExchangeApiResponse {
        val response = exchangeApiClient.getData(request)
        val userId = UUID.randomUUID()

        val exchangeApiResponse = ExchangeApiResponse(
            transactionId = UUID.randomUUID(),
            userId = userId,
            sourceCurrency = request.from,
            sourceAmount = request.amount,
            targetCurrency = response.rates.keys.first(),
            targetAmount = response.rates.values.first(),
            conversionRate = request.amount,
            utcDateTime = response.timestamp
        )

        val transaction = ExchangeTransactionDB(
            userId = userId,
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