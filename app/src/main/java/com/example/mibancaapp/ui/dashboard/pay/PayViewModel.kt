package com.example.mibancaapp.ui.dashboard.pay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mibancaapp.data.local.MovementEntity
import com.example.mibancaapp.data.local.toCard
import com.example.mibancaapp.data.repository.PayRepository
import com.example.mibancaapp.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayViewModel @Inject constructor(
    private val cardRepository: PayRepository
) : ViewModel() {

    private val _userCards = MutableLiveData<List<Card>>()
    val userCards: LiveData<List<Card>> = _userCards

    fun loadCards() {
        viewModelScope.launch {
            val cards = cardRepository.getCardsForCurrentUser()
            _userCards.value = cards.map { it.toCard() }
        }
    }

    fun saveMovement(
        cardId: Int,
        targetCard: String,
        recipient: String,
        reason: String,
        lat: Double?,
        lng: Double?
    ) {
        viewModelScope.launch {
            val movement = MovementEntity(
                sourceCardId = cardId,
                targetCardNumber = targetCard,
                recipientName = recipient,
                reason = reason,
                latitude = lat,
                longitude = lng
            )
            cardRepository.saveMovement(movement)
        }
    }
}
