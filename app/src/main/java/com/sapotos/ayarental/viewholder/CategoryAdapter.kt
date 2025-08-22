package com.sapotos.ayarental.presentation.selectCategoryScreeen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sapotos.ayarental.data.Category
import com.sapotos.ayarental.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onClick: (Category) -> Unit
) : ListAdapter<Category, CategoryAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(a: Category, b: Category) = a.name == b.name
        override fun areContentsTheSame(a: Category, b: Category) = a == b
    }

    inner class VH(val b: ItemCategoryBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: Category) {
            b.tvTitle.text = item.name
            b.imgCar.setImageResource(item.imageRes)
            b.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, v: Int): VH {
        val b = ItemCategoryBinding.inflate(LayoutInflater.from(p.context), p, false)
        return VH(b)
    }
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(getItem(pos))
}
