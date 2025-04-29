package com.currencyconverter.repository

import com.currencyconverter.model.ExchangeTransactionDB
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest
class ExchangeTransactionRepositoryTest {

    @Autowired
    private lateinit var exchangeTransactionRepository: ExchangeTransactionRepository

    @Test
    fun `test save and find transaction`() {
        // Create a transaction
        val transaction = ExchangeTransactionDB(
            id = UUID.randomUUID(),
            userName = "testUser",
            sourceCurrency = "BRL",
            sourceAmount = 10.00,
            targetCurrency = "EUR",
            conversionRate = 0.80,
            utcDateTime = System.currentTimeMillis()
        )

        // Save the transaction
        val savedTransaction = exchangeTransactionRepository.save(transaction)

        // Retrieve the transaction
        val retrievedTransaction = exchangeTransactionRepository.findByUserName(savedTransaction.userName)

        // Assert
        assertTrue(retrievedTransaction.isNotEmpty())
        assertEquals("testUser", retrievedTransaction[0].userName)
    }

    @Test
    fun `test findByUserName with non-existent user`() {
        // Retrieve transactions for a non-existent user
        val transactions = exchangeTransactionRepository.findByUserName("nonExistentUser")

        // Assert
        assertTrue(transactions.isEmpty())
    }

    @Test
    fun `test findByUserName with null userName`() {
        // Retrieve transactions with null userName
        val transactions = exchangeTransactionRepository.findByUserName(null)

        // Assert
        assertTrue(transactions.isEmpty())
    }

    @Test
    fun `test findByUserName with empty userName`() {
        // Retrieve transactions with empty userName
        val transactions = exchangeTransactionRepository.findByUserName("")

        // Assert
        assertTrue(transactions.isEmpty())
    }
}