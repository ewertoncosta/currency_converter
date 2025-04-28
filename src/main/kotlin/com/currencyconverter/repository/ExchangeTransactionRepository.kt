package com.currencyconverter.repository

import com.currencyconverter.model.ExchangeTransactionDB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExchangeTransactionRepository : JpaRepository<ExchangeTransactionDB, Long>{
    fun findByUserId(userId: UUID): List<ExchangeTransactionDB>
}