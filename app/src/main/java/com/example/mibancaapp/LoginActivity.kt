package com.example.mibancaapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mibancaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificar si el usuario ya está logueado
        if (viewModel.isUserLoggedIn()) {
            goToMain()
            return
        }

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observeViewModel() {
        viewModel.authResult.observe(this) { result ->
            when (result) {
                is AuthResult.Success -> {
                    hideError()
                    goToMain()
                }
                is AuthResult.Error -> {
                    showError(result.message)
                }
                else -> {}
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.btnLogin.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.btnLogin.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        viewModel.clearResult()
        hideError()
    }
}