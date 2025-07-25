package com.example.mibancaapp.ui.login.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mibancaapp.data.repository.RegisterRepository
import com.example.mibancaapp.ui.login.login.AuthResult
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
) : ViewModel() {

    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authResult.value = AuthResult.Error("Email y contraseña no pueden estar vacíos")
            return
        }

        if (password.length < 6) {
            _authResult.value = AuthResult.Error("La contraseña debe tener al menos 6 caracteres")
            return
        }

        _loading.value = true
        repository.register(email, password) { success, error ->
            _loading.value = false
            if (success) {
                _authResult.value = AuthResult.Success
            } else {
                _authResult.value = AuthResult.Error(error ?: "Error desconocido")
            }
        }
    }
}