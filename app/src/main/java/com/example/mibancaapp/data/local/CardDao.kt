package com.example.mibancaapp.data.local

import androidx.room.*
import com.example.mibancaapp.model.CardEntity

@Dao
interface CardDao {

    @Query("SELECT * FROM cards WHERE userId = :userId")
    suspend fun getCardsByUser(userId: String): List<CardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity)

    @Delete
    suspend fun deleteCard(card: CardEntity)

    @Query("DELETE FROM cards WHERE id = :cardId")
    suspend fun deleteCardById(cardId: Int)
}
