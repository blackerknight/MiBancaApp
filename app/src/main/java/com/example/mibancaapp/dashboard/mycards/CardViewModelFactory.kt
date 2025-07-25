package com.example.mibancaapp.dashboard.mycards

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mibancaapp.data.repository.CardRepository

class CardViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = CardRepository(context)
        return CardViewModel(repository) as T
    }
}