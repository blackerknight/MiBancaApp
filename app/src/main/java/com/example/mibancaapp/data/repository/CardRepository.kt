package com.example.mibancaapp.data.repository

import com.example.mibancaapp.data.local.CardDao
import com.example.mibancaapp.data.local.CardEntity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CardRepository @Inject constructor(
    private val cardDao: CardDao,
    private val firebaseAuth: FirebaseAuth
) {
    private val userId: String get() = firebaseAuth.currentUser?.uid.orEmpty()

    suspend fun getCards(): List<CardEntity> {
        return cardDao.getCardsByUser(userId)
    }

    suspend fun deleteCard(cardId: Int) {
        cardDao.deleteCardById(cardId)
    }
}