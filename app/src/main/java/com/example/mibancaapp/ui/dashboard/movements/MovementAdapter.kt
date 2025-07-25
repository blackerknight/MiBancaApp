package com.example.mibancaapp.ui.dashboard.movements

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mibancaapp.data.local.MovementEntity
import android.text.format.DateFormat
import androidx.recyclerview.widget.DiffUtil
import com.example.mibancaapp.databinding.ItemMovementBinding

class MovementAdapter() : ListAdapter<MovementEntity, MovementAdapter.MovementViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {
        val binding = ItemMovementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovementViewHolder(private val binding: ItemMovementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

            }
        }

        fun bind(movement: MovementEntity) {
            binding.apply {
                val formattedDate = DateFormat.format("dd/MM/yyyy HH:mm", movement.timestamp)

                textRecipient.text = "Destinatario: ${movement.recipientName}"
                textReason.text = "Motivo: ${movement.reason}"
                textDate.text = "Fecha: $formattedDate"
                textCoordinates.text = "Coordenadas: ${movement.latitude}, ${movement.longitude}"

                textRecipient.setTextColor(Color.parseColor("#222222"))
                textRecipient.textSize = 16f
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MovementEntity>() {
        override fun areItemsTheSame(old: MovementEntity, new: MovementEntity) = old.id == new.id
        override fun areContentsTheSame(old: MovementEntity, new: MovementEntity) = old == new
    }
}
