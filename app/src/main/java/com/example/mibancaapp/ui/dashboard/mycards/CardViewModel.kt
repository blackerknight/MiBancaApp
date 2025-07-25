package com.example.mibancaapp.ui.dashboard.mycards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mibancaapp.data.repository.CardRepository

import com.example.mibancaapp.data.local.toCard
import com.example.mibancaapp.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: CardRepository
) : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun loadCards() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val entities = repository.getCards()
                val cards = entities.map { it.toCard() }

                _cards.value = cards
            } catch (e: Exception) {
                _message.value = "Error loading cards: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteCard(cardId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.deleteCard(cardId)
                _message.value = "Card deleted successfully"
                loadCards()
            } catch (e: Exception) {
                _message.value = "Failed to delete card: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}