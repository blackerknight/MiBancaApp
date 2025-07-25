package com.example.mibancaapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mibancaapp.model.Card

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var userId: String,
    val title: String,
    val number: String,
    val expiry: String
)

fun CardEntity.toCard(): Card {
    return Card(
        id = this.id,
        cardholderName = this.title,
        cardNumber = this.number,
        expirationDate = this.expiry,
        userId = this.userId
    )
}

fun Card.toEntity(): CardEntity {
    return CardEntity(
        id = this.id,
        title = this.cardholderName,
        number = this.cardNumber,
        expiry = this.expirationDate,
        userId = this.userId
    )
}