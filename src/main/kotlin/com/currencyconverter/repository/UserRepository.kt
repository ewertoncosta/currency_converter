package com.currencyconverter.repository

import com.currencyconverter.model.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<Users, Long> {
    fun findByUsername(username: String): Users?
}