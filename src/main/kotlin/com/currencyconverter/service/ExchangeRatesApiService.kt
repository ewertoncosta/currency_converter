package com.currencyconverter.service

import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeRatesResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.net.URI

@Service
class ExchangeRatesApiService(private val restClient: RestClient,
                              @Value("\${exchange-rates-api.url}") private val apiUrl: String) {

    fun getData(request: CurrencyConversionRequest): ExchangeRatesResponse {
        val uri: URI = URI.create(
            "${apiUrl}?base=${request.from}&symbols=${request.to}&amount=${request.amount}&access_key=${request.accessKey}"
        )

        return restClient.get()
            .uri(uri)
            .retrieve()
            .body(ExchangeRatesResponse::class.java)!!
    }
}