package com.example.mibancaapp.ui.dashboard.newcard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mibancaapp.databinding.ActivityAddCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCardBinding
    private val viewModel: AddCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        binding.buttonSave.setOnClickListener {
            saveCard()
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveCard() {
        val cardholderName = binding.editTextCardholderName.text.toString().trim()
        val cardNumber = binding.editTextCardNumber.text.toString().trim()
        val expirationDate = binding.editTextExpirationDate.text.toString().trim()

        viewModel.addCard(cardholderName, cardNumber, expirationDate)
    }

    private fun observeViewModel() {
        viewModel.message.observe(this) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                if (message.contains("successfully")) {
                    finish()
                }
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.buttonSave.isEnabled = !isLoading
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}