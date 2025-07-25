package com.example.mibancaapp.dashboard.mycards.NewCard

import android.content.Context
import com.example.mibancaapp.data.local.AppDatabase
import com.example.mibancaapp.model.CardEntity
import com.google.firebase.auth.FirebaseAuth

class AddCardRepository(context: Context) {
    private val userId: String = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
    private val cardDao = AppDatabase.getInstance(context).cardDao()

    suspend fun addCard(card: CardEntity) {
        card.userId = userId
        cardDao.insertCard(card)
    }
}