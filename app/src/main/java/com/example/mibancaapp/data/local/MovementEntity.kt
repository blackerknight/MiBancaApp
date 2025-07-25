package com.example.mibancaapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movements")
data class MovementEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sourceCardId: Int,
    val targetCardNumber: String,
    val recipientName: String,
    val reason: String,
    val latitude: Double?,
    val longitude: Double?,
    val timestamp: Long = System.currentTimeMillis()
)
