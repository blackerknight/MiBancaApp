package com.example.mibancaapp.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardViewModel : ViewModel() {
    private val repository = CardRepository()

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _operationResult = MutableLiveData<String?>()
    val operationResult: LiveData<String?> = _operationResult

    private fun loadCards() {
        _loading.value = true
        repository.getUserCards { cards, error ->
            _loading.value = false
            if (error != null) {
                _error.value = error
            } else {
                _cards.value = cards
                _error.value = null
            }
        }
    }

    fun addCard(cardHolderName: String, cardNumber: String, expirationDate: String) {
        if (cardHolderName.isBlank() || cardNumber.isBlank() || expirationDate.isBlank()) {
            _error.value = "Todos los campos son obligatorios"
            return
        }

        if (cardNumber.length != 16) {
            _error.value = "El número de tarjeta debe tener 16 dígitos"
            return
        }

        _loading.value = true
        val card = Card(
            cardHolderName = cardHolderName,
            cardNumber = cardNumber,
            expirationDate = expirationDate
        )

        repository.addCard(card) { success, error ->
            _loading.value = false
            if (success) {
                _operationResult.value = "Tarjeta agregada exitosamente"
                loadCards() // Recargar la lista
            } else {
                _error.value = error
            }
        }
    }

    fun deleteCard(cardId: String) {
        _loading.value = true
        repository.deleteCard(cardId) { success, error ->
            _loading.value = false
            if (success) {
                _operationResult.value = "Tarjeta eliminada exitosamente"
                loadCards() // Recargar la lista
            } else {
                _error.value = error
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun clearOperationResult() {
        _operationResult.value = null
    }
}