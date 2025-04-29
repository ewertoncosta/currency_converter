package com.currencyconverter.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Users {
    @Id
    lateinit var username: String
    lateinit var password: String
}