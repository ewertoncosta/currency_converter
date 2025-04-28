package com.currencyconverter.service

import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeApiResponse
import com.currencyconverter.model.ExchangeRatesResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.net.URI

@Service
class ExchangeRatesApi(private val restClient: RestClient) {

    fun getData(request: CurrencyConversionRequest): ExchangeRatesResponse {
        val uri: URI = URI.create(
            "http://api.exchangeratesapi.io/latest?base=${request.from}&symbols=${request.to}&amount=${request.amount}&access_key=${request.accessKey}"
        )

        return restClient.get()
            .uri(uri)
            .retrieve()
            .body(ExchangeRatesResponse::class.java)!!
    }
}