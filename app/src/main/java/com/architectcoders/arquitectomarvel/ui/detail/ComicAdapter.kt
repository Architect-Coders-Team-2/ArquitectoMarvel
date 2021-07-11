package com.architectcoders.arquitectomarvel.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.architectcoders.arquitectomarvel.databinding.ComicItemBinding
import com.architectcoders.domain.comic.Comic
import javax.inject.Inject

class ComicAdapter @Inject constructor() : ListAdapter<Comic, ComicViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding =
            ComicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val detailedComicEntity = getItem(position)
        holder.bind(detailedComicEntity)
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<Comic>() {
    override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean =
        oldItem.resourceURI == newItem.resourceURI

    override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean =
        oldItem == newItem
}
