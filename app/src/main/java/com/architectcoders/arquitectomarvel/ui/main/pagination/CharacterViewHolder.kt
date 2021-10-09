package com.architectcoders.arquitectomarvel.ui.main.pagination

import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.databinding.CharacterItemBinding
import com.architectcoders.arquitectomarvel.ui.common.loadUrl
import com.architectcoders.domain.character.Character

class CharacterViewHolder(private val binding: CharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val imageCharacter = binding.characterImage

    fun bind(character: Character) {
        binding.characterText.text = character.name
        binding.characterImage.loadUrl(
            character.thumbnail?.path,
            character.thumbnail?.extension
        )
    }
}
