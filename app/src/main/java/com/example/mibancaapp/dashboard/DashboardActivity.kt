package com.example.mibancaapp.dashboard

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mibancaapp.dashboard.mycards.Card
import com.example.mibancaapp.dashboard.mycards.CardRepository
import com.example.mibancaapp.databinding.ActivityDashboardBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

        // TEST: Add a test button to insert sample data
        addTestButton()
    }

    private fun setupViewPager() {
        val adapter = DashboardPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "My Cards"
                1 -> "Pay"
                2 -> "My Movements"
                else -> ""
            }
        }.attach()
    }

    // TEST FUNCTION - Remove this in production
    private fun addTestButton() {
        val testButton = Button(this).apply {
            text = "TEST: Insert Sample Card"
            setOnClickListener {
                insertTestCard()
            }
        }

        // Add the button to the layout (temporary)
        (binding.root as ViewGroup).addView(testButton)
    }

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // TEST FUNCTION - Remove this in production
    private fun insertTestCard() {
        val repository = CardRepository()
        val currentUser = FirebaseAuth.getInstance().currentUser

        Log.d("Firebase","Firebase: tiene internet " + isOnline(applicationContext))

        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val testCard = Card(
            cardholderName = "John Doe Test",
            cardNumber = "1234567890123456",
            expirationDate = "12/25",
            userId = currentUser.uid
        )

        Toast.makeText(this, "Inserting test card...", Toast.LENGTH_SHORT).show()

        repository.addCard(testCard) { success ->
            if (success) {
                Toast.makeText(this, "✅ Test card inserted successfully!", Toast.LENGTH_LONG).show()
                // Log for debugging
                println("✅ Card inserted in Firestore with userId: ${currentUser.uid}")
            } else {
                Toast.makeText(this, "❌ Failed to insert test card", Toast.LENGTH_LONG).show()
                println("❌ Failed to insert card in Firestore")
            }
        }
    }
}