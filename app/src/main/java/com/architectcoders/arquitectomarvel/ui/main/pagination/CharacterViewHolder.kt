package com.architectcoders.arquitectomarvel.ui.main.pagination

import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.databinding.CharacterItemBinding
import com.architectcoders.arquitectomarvel.ui.common.loadUrl
import com.architectcoders.domain.characters.Result as CharacterResult

class CharacterViewHolder(private val binding: CharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(character: CharacterResult) {
        binding.heroText.text = character.name
        binding.heroImage.loadUrl(
            character.thumbnail?.path,
            character.thumbnail?.extension
        )
    }
}
