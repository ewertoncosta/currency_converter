package com.currencyconverter.service

import com.currencyconverter.exception.InvalidAmountException
import com.currencyconverter.exception.InvalidCurrencyCodeException
import com.currencyconverter.model.CurrencyConversionRequest
import com.currencyconverter.model.ExchangeRatesResponse
import com.currencyconverter.model.ExchangeTransactionDB
import com.currencyconverter.model.Users
import com.currencyconverter.repository.ExchangeTransactionRepository
import com.currencyconverter.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDate

@SpringBootTest
class ExchangeConvertServiceTest {

    private val exchangeApiClient = mock(ExchangeRatesApiService::class.java)
    private val exchangeTransactionRepository = mock(ExchangeTransactionRepository::class.java)
    private val userRepository = mock(UserRepository::class.java)

    private val exchangeConvertService = ExchangeConvertService(
        exchangeApiClient,
        exchangeTransactionRepository,
        userRepository
    )

    @BeforeEach
    fun setup() {
        // Mock SecurityContext and Authentication
        val authentication = mock(Authentication::class.java)
        val securityContext = mock(SecurityContext::class.java)

        // Define behavior for the mocked objects
        `when`(securityContext.authentication).thenReturn(authentication)
        `when`(authentication.name).thenReturn("testUser")

        // Set the mocked SecurityContext in SecurityContextHolder
        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    fun `test authentication retrieval`() {
        // Retrieve the authentication from SecurityContextHolder
        val authentication = SecurityContextHolder.getContext().authentication

        // Assert the username
        assert(authentication.name == "testUser")
    }

    @Test
    fun `test convertCurrency with valid data`() {
        // Mock input
        val request = CurrencyConversionRequest("abcd-1234-abcd-1234","USD", "EUR", 100.0)
        val exchangeRatesResponse = ExchangeRatesResponse(
            base = "USD",
            rates = mapOf("EUR" to 0.85),
            date = LocalDate.now(),
            success = true,
            timestamp = System.currentTimeMillis()
        )
        val user = Users().apply {
            username = "testUser"
            password = "password"
        }

        // Mock behavior
        `when`(exchangeApiClient.getData(request)).thenReturn(exchangeRatesResponse)
        `when`(userRepository.findByUsername("testUser")).thenReturn(user)

        // Call the method
        val result = exchangeConvertService.convertCurrency(request)

        // Verify and assert
        assertNotNull(result)
        assertEquals("USD", result.sourceCurrency)
        assertEquals("EUR", result.targetCurrency)
        assertEquals(0.85, result.targetAmount)
        verify(exchangeTransactionRepository, times(1)).save(any(ExchangeTransactionDB::class.java))
    }

    @Test
    fun `test convertCurrency with invalid currency code`() {
        // Mock input
        val request = CurrencyConversionRequest("abcd-1234-abcd-1234","INVALID", "EUR", 10.0)

        // Call the method and assert exception
        val exception = assertThrows(InvalidCurrencyCodeException::class.java) {
            exchangeConvertService.convertCurrency(request)
        }
        assertEquals("Invalid currency code: INVALID", exception.message)
    }

    @Test
    fun `test convertCurrency with invalid amount`() {
        // Mock input
        val request = CurrencyConversionRequest("abcd-1234-abcd-1234","USD", "EUR", -10.0)

        // Call the method and assert exception
        val exception = assertThrows(InvalidAmountException::class.java) {
            exchangeConvertService.convertCurrency(request)
        }
        assertEquals("Amount must be greater than zero", exception.message)
    }
}