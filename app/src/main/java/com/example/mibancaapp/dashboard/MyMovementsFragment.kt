package com.example.mibancaapp.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mibancaapp.databinding.FragmentMyMovementsBinding

class MyMovementsFragment : Fragment() {
    private var _binding: FragmentMyMovementsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyMovementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textPlaceholder.text = "Movements history coming soon"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}