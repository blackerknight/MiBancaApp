package com.example.mibancaapp.ui.login.login

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    val currentUser: FirebaseUser? get() = firebaseAuth.currentUser

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseAuth", "Fallo en login: ${e.message}", e)
            }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }
}