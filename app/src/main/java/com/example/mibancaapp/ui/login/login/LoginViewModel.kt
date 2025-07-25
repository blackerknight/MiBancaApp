package com.example.mibancaapp.ui.login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()

    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    val currentUser: FirebaseUser? get() = repository.currentUser

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authResult.value = AuthResult.Error("Email y contraseña no pueden estar vacíos")
            return
        }

        _loading.value = true
        repository.login(email, password) { success, error ->
            _loading.value = false
            if (success) {
                _authResult.value = AuthResult.Success
            } else {
                _authResult.value = AuthResult.Error(error ?: "Error desconocido")
            }
        }
    }

    fun logout() {
        repository.logout()
        _authResult.value = AuthResult.Logout
    }

    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }
}

sealed class AuthResult {
    object Success : AuthResult()
    object Logout : AuthResult()
    data class Error(val message: String) : AuthResult()
}