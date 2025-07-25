package com.example.mibancaapp.ui.dashboard.movements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mibancaapp.R
import com.example.mibancaapp.data.local.MovementEntity
import android.text.format.DateFormat
import androidx.recyclerview.widget.DiffUtil

class MovementAdapter : ListAdapter<MovementEntity, MovementAdapter.MovementViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movement, parent, false)
        return MovementViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textInfo = itemView.findViewById<TextView>(R.id.textInfo)

        fun bind(movement: MovementEntity) {
            val formattedDate = DateFormat.format("dd/MM/yyyy HH:mm", movement.timestamp)
            textInfo.text = """
                Destinatario: ${movement.recipientName}
                Motivo: ${movement.reason}
                Fecha: $formattedDate
                Coordenadas: ${movement.latitude}, ${movement.longitude}
            """.trimIndent()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MovementEntity>() {
        override fun areItemsTheSame(old: MovementEntity, new: MovementEntity) = old.id == new.id
        override fun areContentsTheSame(old: MovementEntity, new: MovementEntity) = old == new
    }
}
