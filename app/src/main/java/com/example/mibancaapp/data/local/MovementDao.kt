package com.example.mibancaapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovement(movement: MovementEntity)

    @Query("SELECT * FROM movements ORDER BY timestamp DESC")
    suspend fun getAllMovements(): List<MovementEntity>
}
