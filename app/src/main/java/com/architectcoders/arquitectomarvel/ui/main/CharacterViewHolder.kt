package com.architectcoders.arquitectomarvel.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.databinding.CharacterItemBinding
import com.architectcoders.arquitectomarvel.ui.common.loadUrl
import com.architectcoders.domain.characters.Result

class CharacterViewHolder(private val binding: CharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(mediaService: Result) {
        binding.heroText.text = mediaService.name
        binding.heroImage.loadUrl(
            mediaService.thumbnail?.path,
            mediaService.thumbnail?.extension
        )
    }
}
