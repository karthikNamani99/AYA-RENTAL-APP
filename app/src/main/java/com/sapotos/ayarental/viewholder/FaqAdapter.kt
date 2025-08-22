package com.sapotos.ayarental.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.sapotos.ayarental.databinding.ItemFaqBinding

data class FaqItem(
    val question: String,
    val answer: String,
    var expanded: Boolean = false
)

class FaqAdapter(
    private val items: MutableList<FaqItem>
) : RecyclerView.Adapter<FaqAdapter.FaqVH>() {

    inner class FaqVH(val binding: ItemFaqBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFaqBinding.inflate(inflater, parent, false)
        return FaqVH(binding)
    }

    override fun onBindViewHolder(holder: FaqVH, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvQuestion.text = item.question
            tvAnswer.text = item.answer
            tvAnswer.visibility = if (item.expanded) View.VISIBLE else View.GONE
            ivArrow.rotation = if (item.expanded) 180f else 0f

            // Toggle with animation
            rowHeader.setOnClickListener {
                item.expanded = !item.expanded
                TransitionManager.beginDelayedTransition(
                    root as ViewGroup, AutoTransition().setDuration(150)
                )
                tvAnswer.visibility = if (item.expanded) View.VISIBLE else View.GONE
                ivArrow.animate().rotation(if (item.expanded) 180f else 0f).setDuration(150).start()
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
