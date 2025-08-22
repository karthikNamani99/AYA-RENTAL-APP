package com.sapotos.ayarental.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sapotos.ayarental.databinding.ItemBookingCardBinding

class BookingHistoryAdapter(
    private val onDelete: (BookingItem) -> Unit,
    private val onItemClick: ((BookingItem) -> Unit)? = null
) : ListAdapter<BookingItem, BookingHistoryAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<BookingItem>() {
        override fun areItemsTheSame(old: BookingItem, new: BookingItem) =
            old.bookingId == new.bookingId
        override fun areContentsTheSame(old: BookingItem, new: BookingItem) = old == new
    }

    inner class VH(val binding: ItemBookingCardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnDelete.setOnClickListener {
                val pos = absoluteAdapterPosition
                if (pos != RecyclerView.NO_POSITION) onDelete(getItem(pos))
            }
            binding.root.setOnClickListener {
                val pos = absoluteAdapterPosition
                if (pos != RecyclerView.NO_POSITION) onItemClick?.invoke(getItem(pos))
            }
        }
        fun bind(item: BookingItem) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val b = ItemBookingCardBinding.inflate(inflater, parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))
}
