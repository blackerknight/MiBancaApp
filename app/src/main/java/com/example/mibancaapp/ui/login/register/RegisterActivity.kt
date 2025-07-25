package com.example.mibancaapp.ui.login.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mibancaapp.databinding.ActivityRegisterBinding
import com.example.mibancaapp.ui.login.login.AuthResult
import com.example.mibancaapp.ui.login.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (password != confirmPassword) {
                showError("Las contraseñas no coinciden")
                return@setOnClickListener
            }

            viewModel.register(email, password)
        }

        binding.tvLogin.setOnClickListener {
            finish()
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
                binding.btnRegister.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.btnRegister.visibility = View.VISIBLE
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
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        hideError()
    }
}