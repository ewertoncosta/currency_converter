package com.currencyconverter.repository

import com.currencyconverter.model.ExchangeTransactionDB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ExchangeTransactionRepository : JpaRepository<ExchangeTransactionDB, Long>{
    fun findByUserName(userName: String): List<ExchangeTransactionDB>
}