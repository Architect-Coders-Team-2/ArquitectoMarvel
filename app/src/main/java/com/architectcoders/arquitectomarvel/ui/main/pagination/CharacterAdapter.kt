package com.architectcoders.arquitectomarvel.ui.main.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.architectcoders.arquitectomarvel.databinding.CharacterItemBinding
import com.architectcoders.domain.characters.Result

class CharacterAdapter(private val listener: (Result, View) -> Unit) :
    PagingDataAdapter<Result, CharacterViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val result = getItem(position)
        if (result != null) {
            holder.bind(result)
            holder.itemView.setOnClickListener {
                listener(result, it)
            }
        }
    }
}
