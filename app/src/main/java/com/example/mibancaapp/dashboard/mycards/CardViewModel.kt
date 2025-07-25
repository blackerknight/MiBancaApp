package com.example.mibancaapp.dashboard.mycards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CardViewModel : ViewModel() {
    private val repository = CardRepository()

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun loadCards() {
        _isLoading.value = true
        repository.getCards { cards ->
            _isLoading.value = false
            _cards.value = cards
        }
    }

    fun addCard(cardholderName: String, cardNumber: String, expirationDate: String) {
        if (cardholderName.isBlank() || cardNumber.isBlank() || expirationDate.isBlank()) {
            _message.value = "Please fill all fields"
            return
        }

        _isLoading.value = true
        val card = Card(
            cardholderName = cardholderName,
            cardNumber = cardNumber,
            expirationDate = expirationDate
        )

        repository.addCard(card) { success ->
            _isLoading.value = false
            if (success) {
                _message.value = "Card added successfully"
                loadCards() // Reload cards after adding
            } else {
                _message.value = "Failed to add card"
            }
        }
    }

    fun deleteCard(cardId: String) {
        _isLoading.value = true
        repository.deleteCard(cardId) { success ->
            _isLoading.value = false
            if (success) {
                _message.value = "Card deleted successfully"
                loadCards() // Reload cards after deleting
            } else {
                _message.value = "Failed to delete card"
            }
        }
    }
}