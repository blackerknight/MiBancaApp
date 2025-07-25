package com.example.mibancaapp.ui.login.register

import com.google.firebase.auth.FirebaseAuth

class RegisterRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }
}