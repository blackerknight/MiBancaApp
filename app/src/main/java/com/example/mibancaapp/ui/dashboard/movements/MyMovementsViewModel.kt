package com.example.mibancaapp.ui.dashboard.movements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mibancaapp.data.local.MovementEntity
import com.example.mibancaapp.data.repository.MyMovementsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMovementsViewModel @Inject constructor(
    private val repository: MyMovementsRepository
) : ViewModel() {

    private val _movements = MutableLiveData<List<MovementEntity>>()
    val movements: LiveData<List<MovementEntity>> = _movements

    fun loadMovements() {
        viewModelScope.launch {
            val result = repository.getAllMovements()
            _movements.value = result
        }
    }
}
