package com.example.mibancaapp.data.repository

import com.example.mibancaapp.data.local.CardDao
import com.example.mibancaapp.data.local.CardEntity
import com.example.mibancaapp.data.local.MovementDao
import com.example.mibancaapp.data.local.MovementEntity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PayRepository @Inject constructor(
    private val cardDao: CardDao,
    private val movementDao: MovementDao,
    private val firebaseAuth: FirebaseAuth
) {
    private val userId: String get() = firebaseAuth.currentUser?.uid.orEmpty()

    suspend fun getCardsForCurrentUser(): List<CardEntity> {
        return cardDao.getCardsByUser(userId)
    }

    suspend fun saveMovement(movement: MovementEntity) {
        movementDao.insertMovement(movement)
    }
}
