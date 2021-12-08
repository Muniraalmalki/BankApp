package com.example.muniraalmalki

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muniraalmalki.databinding.ItemRowBinding

class RecyclerViewAdapter(private val displayList:ArrayList<String>):RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
       return ItemViewHolder(
           ItemRowBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false
           )
       )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = displayList[position]
        holder.binding.apply {
            tvItem.text = item
        }
    }

    override fun getItemCount(): Int{
        return displayList.size
    }

}