package com.example.mibancaapp.data.repository

import android.content.Context
import com.example.mibancaapp.data.local.AppDatabase
import com.example.mibancaapp.data.local.CardDao
import com.example.mibancaapp.data.local.CardEntity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddCardRepository @Inject constructor(
    private val cardDao: CardDao,
    private val firebaseAuth: FirebaseAuth
) {
    private val userId: String get() = firebaseAuth.currentUser?.uid.orEmpty()

    suspend fun addCard(card: CardEntity) {
        card.userId = userId
        cardDao.insertCard(card)
    }
}