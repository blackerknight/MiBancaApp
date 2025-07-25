package com.example.mibancaapp.ui.dashboard.pay

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mibancaapp.databinding.FragmentPayBinding
import com.example.mibancaapp.model.Card
import com.example.mibancaapp.ui.dashboard.TransferSuccess.TransferSuccessActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayFragment : Fragment() {

    private var _binding: FragmentPayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PayViewModel by viewModels()
    private lateinit var cardAdapter: ArrayAdapter<String>
    private var selectedCard: Card? = null
    private var cardList: List<Card> = emptyList()

    private lateinit var locationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPayBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        viewModel.userCards.observe(viewLifecycleOwner) { cards ->
            cardList = cards

            val cardTitles = cards.map { it.cardholderName + " - " + it.cardNumber.takeLast(4) }
            cardAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cardTitles)
            cardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCards.adapter = cardAdapter
        }

        binding.spinnerCards.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedCard = cardList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCard = null
            }
        }

        binding.btnPay.setOnClickListener {
            if (hasLocationPermission()) {
                getCurrentLocationAndSaveMovement()
            } else {
                requestLocationPermission()
            }
        }

        viewModel.loadCards()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCards()
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1001
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION_PERMISSION &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocationAndSaveMovement()
        } else {
            Toast.makeText(requireContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocationAndSaveMovement() {
        locationClient.lastLocation.addOnSuccessListener { location ->
            val lat = location?.latitude
            val lng = location?.longitude

            val originCard = selectedCard!!
            val targetCard = binding.etTargetCard.text.toString()
            val recipient = binding.etRecipientName.text.toString()
            val reason = binding.etPaymentReason.text.toString()

            viewModel.saveMovement(
                cardId = originCard.id,
                targetCard = targetCard,
                recipient = recipient,
                reason = reason,
                lat = lat,
                lng = lng
            )

            // Clear input fields
            binding.etTargetCard.text.clear()
            binding.etRecipientName.text.clear()
            binding.etPaymentReason.text.clear()

            // Launch TransferSuccessActivity
            val intent = Intent(requireContext(), TransferSuccessActivity::class.java).apply {
                putExtra("cardTitle", originCard.cardholderName)
                putExtra("targetCard", targetCard)
                putExtra("recipient", recipient)
                putExtra("reason", reason)
            }

            startActivity(intent)

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}