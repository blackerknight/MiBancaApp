package com.example.mibancaapp.dashboard

data class Card(
    val id: String = "",
    val cardHolderName: String = "",
    val cardNumber: String = "",
    val expirationDate: String = "",
    val userId: String = ""
) {
    constructor() : this("", "", "", "", "")
}