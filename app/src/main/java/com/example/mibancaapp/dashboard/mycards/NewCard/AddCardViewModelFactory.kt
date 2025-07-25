package com.example.mibancaapp.dashboard.mycards.NewCard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mibancaapp.data.repository.AddCardRepository
import com.example.mibancaapp.ui.dashboard.newcard.AddCardViewModel

class AddCardViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = AddCardRepository(context)
        return AddCardViewModel(repository) as T
    }
}