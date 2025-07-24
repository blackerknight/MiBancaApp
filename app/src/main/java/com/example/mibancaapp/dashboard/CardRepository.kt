package com.example.mibancaapp.dashboard

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CardRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun getUserId(): String? = auth.currentUser?.uid

    fun getCards(callback: (List<Card>) -> Unit) {
        val userId = getUserId()

        if (userId == null) {
            callback(emptyList())
            return
        }

        firestore.collection("cards")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val cards = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Card::class.java)?.copy(id = doc.id)
                }
                callback(cards)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun addCard(card: Card, callback: (Boolean) -> Unit) {
        val userId = getUserId()
        if (userId == null) {
            callback(false)
            return
        }

        val cardWithUserId = card.copy(userId = userId)
        firestore.collection("cards")
            .add(cardWithUserId)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun deleteCard(cardId: String, callback: (Boolean) -> Unit) {
        firestore.collection("cards")
            .document(cardId)
            .delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
}