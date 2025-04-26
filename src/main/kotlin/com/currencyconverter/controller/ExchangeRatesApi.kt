import com.currencyconverter.model.ExchangeApiResponse
import org.springframework.web.client.RestClient

class ExchangeRatesApi(private val restClient: RestClient) {
    fun getData(): ExchangeApiResponse? {
        return restClient.get()
            .uri("http://api.exchangeratesapi.io/latest?base=EUR")
            .retrieve()
            .body(ExchangeApiResponse::class.java)
    }
}