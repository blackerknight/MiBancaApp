package com.example.mibancaapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mibancaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificar si el usuario está logueado
        if (!viewModel.isUserLoggedIn()) {
            goToLogin()
            return
        }

        setupUI()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupUI() {
        val user = viewModel.currentUser
        binding.tvUserEmail.text = user?.email ?: "Usuario"
    }

    private fun setupClickListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun observeViewModel() {
        viewModel.authResult.observe(this) { result ->
            when (result) {
                is AuthResult.Logout -> {
                    goToLogin()
                }
                else -> {}
            }
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}