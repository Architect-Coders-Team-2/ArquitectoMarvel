package com.architectcoders.arquitectomarvel.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.architectcoders.arquitectomarvel.data.database.FavoriteCharacterEntity
import com.architectcoders.arquitectomarvel.data.database.toDomainCharacter
import com.architectcoders.arquitectomarvel.databinding.CharacterItemBinding
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterViewHolder
import com.architectcoders.domain.character.Character

class FavoriteCharacterAdapter(private val listener: (Character, View) -> Unit) :
    ListAdapter<FavoriteCharacterEntity, CharacterViewHolder>(DiffCallback) {

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    companion object DiffCallback : DiffUtil.ItemCallback<FavoriteCharacterEntity>() {
        override fun areItemsTheSame(
            oldItem: FavoriteCharacterEntity,
            newItem: FavoriteCharacterEntity
        ) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FavoriteCharacterEntity,
            newItem: FavoriteCharacterEntity
        ) =
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
                listener(characterEntity.toDomainCharacter, holder.imageCharacter)
            }
        }
    }
}
