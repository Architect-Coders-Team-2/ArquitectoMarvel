package com.architectcoders.arquitectomarvel.ui.detail

import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ComicItemBinding
import com.architectcoders.arquitectomarvel.ui.common.loadUrl
import com.architectcoders.domain.comic.Comic

class ComicViewHolder(private val binding: ComicItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(comic: Comic) {
        binding.comicImage.loadUrl(comic.thumbnail?.path, comic.thumbnail?.extension)
        binding.comicTitle.text =
            comic.title ?: binding.root.context.getString(R.string.content_not_available)
    }
}
