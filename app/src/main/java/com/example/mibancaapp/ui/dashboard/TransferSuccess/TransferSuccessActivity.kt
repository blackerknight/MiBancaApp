package com.example.mibancaapp.ui.dashboard.TransferSuccess

import dagger.hilt.android.AndroidEntryPoint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mibancaapp.databinding.ActivityTransferSuccessBinding

@AndroidEntryPoint
class TransferSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransferSuccessBinding

    private fun maskAccount(account: String): String {
        return if (account.length > 4) {
            "*".repeat(account.length - 4) + account.takeLast(4)
        } else {
            account
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardTitle = intent.getStringExtra("cardTitle") ?: ""
        val targetCard = intent.getStringExtra("targetCard") ?: ""
        val recipient = intent.getStringExtra("recipient") ?: ""
        val reason = intent.getStringExtra("reason") ?: ""

        binding.textOrigen.text = cardTitle
        binding.textDestino.text = maskAccount(targetCard)
        binding.textNombre.text = recipient
        binding.textMotivo.text = reason

        binding.btnAccept.setOnClickListener {
            finish()
        }
    }
}

