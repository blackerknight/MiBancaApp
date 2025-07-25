package com.example.mibancaapp.dashboard.mycards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mibancaapp.databinding.ItemCardBinding

class CardAdapter(
    private val onDeleteClick: (Card) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var cards = listOf<Card>()

    fun updateCards(newCards: List<Card>) {
        cards = newCards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun getItemCount() = cards.size

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) {
            binding.apply {
                textCardholderName.text = card.cardholderName
                textCardNumber.text = "•••• •••• •••• ${card.cardNumber.takeLast(4)}"
                textExpirationDate.text = card.expirationDate

                buttonDelete.setOnClickListener {
                    onDeleteClick(card)
                }
            }
        }
    }
}