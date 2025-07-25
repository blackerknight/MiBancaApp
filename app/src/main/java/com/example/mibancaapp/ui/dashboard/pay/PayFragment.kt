package com.example.mibancaapp.ui.dashboard.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mibancaapp.databinding.FragmentPayBinding

class PayFragment : Fragment() {
    private var _binding: FragmentPayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textPlaceholder.text = "Pay functionality coming soon"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}