package com.example.mibancaapp.model

data class Card(
    val id: Int = 0,
    val cardholderName: String = "",
    val cardNumber: String = "",
    val expirationDate: String = "",
    val userId: String = ""
)