package com.example.mibancaapp.dashboard

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CardRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val currentUserId: String?
        get() = auth.currentUser?.uid

    fun addCard(card: Card, callback: (Boolean, String?) -> Unit) {
        val userId = currentUserId
        if (userId == null) {
            callback(false, "Usuario no autenticado")
            return
        }

        val cardWithUserId = card.copy(userId = userId)

        firestore.collection("cards")
            .add(cardWithUserId)
            .addOnSuccessListener { documentReference ->
                val cardWithId = cardWithUserId.copy(id = documentReference.id)
                documentReference.set(cardWithId)
                    .addOnSuccessListener {
                        callback(true, null)
                    }
                    .addOnFailureListener { exception ->
                        callback(false, exception.message)
                    }
            }
            .addOnFailureListener { exception ->
                callback(false, exception.message)
            }
    }

    fun getUserCards(callback: (List<Card>, String?) -> Unit) {
        val userId = currentUserId
        if (userId == null) {
            callback(emptyList(), "Usuario no autenticado")
            return
        }

        firestore.collection("cards")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val cards = documents.mapNotNull { document ->
                    document.toObject(Card::class.java)
                }
                callback(cards, null)
            }
            .addOnFailureListener { exception ->
                callback(emptyList(), exception.message)
            }
    }

    fun deleteCard(cardId: String, callback: (Boolean, String?) -> Unit) {
        firestore.collection("cards")
            .document(cardId)
            .delete()
            .addOnSuccessListener {
                callback(true, null)
            }
            .addOnFailureListener { exception ->
                callback(false, exception.message)
            }
    }
}