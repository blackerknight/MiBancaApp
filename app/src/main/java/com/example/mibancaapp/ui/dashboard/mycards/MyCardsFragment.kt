package com.example.mibancaapp.ui.dashboard.mycards

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mibancaapp.ui.dashboard.newcard.AddCardActivity
import com.example.mibancaapp.databinding.FragmentMyCardsBinding
import com.example.mibancaapp.model.Card
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCardsFragment : Fragment() {
    private var _binding: FragmentMyCardsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardViewModel by viewModels()
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        viewModel.loadCards()

        binding.fabAddCard.setOnClickListener {
            val intent = Intent(requireContext(), AddCardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCards()
    }

    private fun setupRecyclerView() {
        adapter = CardAdapter { card ->
            showDeleteConfirmation(card)
        }
        binding.recyclerViewCards.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            if (cards.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.recyclerViewCards.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.recyclerViewCards.visibility = View.VISIBLE
                adapter.updateCards(cards)
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showDeleteConfirmation(card: Card) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Card")
            .setMessage("Are you sure you want to delete this card?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteCard(card.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}