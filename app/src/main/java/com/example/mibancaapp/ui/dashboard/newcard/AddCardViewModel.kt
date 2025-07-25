package com.example.mibancaapp.ui.dashboard.newcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mibancaapp.model.Card
import com.example.mibancaapp.data.local.toEntity
import com.example.mibancaapp.data.repository.AddCardRepository
import kotlinx.coroutines.launch

class AddCardViewModel(private val repository: AddCardRepository) : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun addCard(cardholderName: String, cardNumber: String, expirationDate: String) {
        if (cardholderName.isBlank() || cardNumber.isBlank() || expirationDate.isBlank()) {
            _message.value = "Please fill all fields"
            return
        }

        val card = Card(
            userId = "",
            cardholderName = cardholderName,
            cardNumber = cardNumber,
            expirationDate = expirationDate
        )

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val entity = card.toEntity()
                repository.addCard(entity)
                _message.value = "Card added successfully"
            } catch (e: Exception) {
                _message.value = "Failed to add card: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}