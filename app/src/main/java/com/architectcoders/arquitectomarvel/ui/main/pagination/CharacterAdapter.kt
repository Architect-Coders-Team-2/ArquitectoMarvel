package com.architectcoders.arquitectomarvel.ui.main.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.architectcoders.arquitectomarvel.data.database.HeroEntity
import com.architectcoders.arquitectomarvel.data.database.toDomainCharacter
import com.architectcoders.arquitectomarvel.databinding.CharacterItemBinding
import com.architectcoders.domain.heros.Hero

class CharacterAdapter(private val listener: (Hero, View) -> Unit) :
    PagingDataAdapter<HeroEntity, CharacterViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<HeroEntity>() {
        override fun areItemsTheSame(oldItem: HeroEntity, newItem: HeroEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: HeroEntity, newItem: HeroEntity) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { characterEntity ->
            holder.bind(characterEntity.toDomainCharacter)
            holder.itemView.setOnClickListener {
                listener(characterEntity.toDomainCharacter, it)
            }
        }
    }
}
