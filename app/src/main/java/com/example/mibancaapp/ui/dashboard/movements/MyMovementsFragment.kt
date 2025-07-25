package com.example.mibancaapp.ui.dashboard.movements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mibancaapp.databinding.FragmentMyMovementsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyMovementsFragment : Fragment() {

    private var _binding: FragmentMyMovementsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyMovementsViewModel by viewModels()

    private lateinit var adapter: MovementAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyMovementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MovementAdapter()
        binding.recyclerViewMovements.adapter = adapter
        binding.recyclerViewMovements.layoutManager = LinearLayoutManager(requireContext())

        viewModel.movements.observe(viewLifecycleOwner) { movements ->
            adapter.submitList(movements)
        }

        viewModel.loadMovements()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMovements()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
