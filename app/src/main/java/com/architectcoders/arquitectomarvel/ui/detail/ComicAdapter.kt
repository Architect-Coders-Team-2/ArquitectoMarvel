package com.architectcoders.arquitectomarvel.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.architectcoders.arquitectomarvel.databinding.ComicItemBinding
import com.architectcoders.domain.comics.Comic as ComicResult

class ComicAdapter : ListAdapter<ComicResult, ComicViewHolder>(DiffUtilCallback) {

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

private object DiffUtilCallback : DiffUtil.ItemCallback<ComicResult>() {
    override fun areItemsTheSame(oldItem: ComicResult, newItem: ComicResult): Boolean =
        oldItem.resourceURI == newItem.resourceURI

    override fun areContentsTheSame(oldItem: ComicResult, newItem: ComicResult): Boolean =
        oldItem == newItem
}
