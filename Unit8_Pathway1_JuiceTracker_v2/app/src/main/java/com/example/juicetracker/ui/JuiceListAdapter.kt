package com.example.juicetracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.juicetracker.data.Juice
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.databinding.ListItemBinding

class JuiceListAdapter(
    private var onEdit: (Juice) -> Unit,
    private var onDelete: (Juice) -> Unit
) : ListAdapter<Juice, JuiceListAdapter.JuiceListViewHolder>(JuiceDiffCallback()) {

    class JuiceListViewHolder(
        private val binding: ListItemBinding,
        private val onEdit: (Juice) -> Unit,
        private val onDelete: (Juice) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val nameView = binding.name
        private val description = binding.description
        private val drinkImage = binding.drinkColorOverlay
        private val ratingBar = binding.ratingBar

        fun bind(juice: Juice) {
            nameView.text = juice.name
            description.text = juice.description
            drinkImage.setColorFilter(
                JuiceColor.valueOf(juice.color).color,
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            ratingBar.rating = juice.rating.toFloat()
            binding.deleteButton.setOnClickListener {
                onDelete(juice)
            }
            binding.root.setOnClickListener {
                onEdit(juice)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = JuiceListViewHolder(
        ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onEdit,
        onDelete
    )

    override fun onBindViewHolder(holder: JuiceListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class JuiceDiffCallback : DiffUtil.ItemCallback<Juice>() {
    override fun areItemsTheSame(oldItem: Juice, newItem: Juice): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Juice, newItem: Juice): Boolean {
        return oldItem == newItem
    }
}
