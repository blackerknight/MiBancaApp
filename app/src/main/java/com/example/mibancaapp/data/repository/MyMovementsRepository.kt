package com.example.mibancaapp.data.repository

import com.example.mibancaapp.data.local.MovementDao
import com.example.mibancaapp.data.local.MovementEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyMovementsRepository @Inject constructor(
    private val movementDao: MovementDao
) {
    suspend fun getAllMovements(): List<MovementEntity> {
        return movementDao.getAllMovements()
    }
}
