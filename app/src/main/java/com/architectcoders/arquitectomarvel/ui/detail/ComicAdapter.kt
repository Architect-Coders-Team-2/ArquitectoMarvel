package com.architectcoders.arquitectomarvel.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.local.entities.DetailedComicEntity
import com.architectcoders.arquitectomarvel.databinding.ComicItemBinding
import com.architectcoders.arquitectomarvel.model.loadUrl

class ComicAdapter : ListAdapter<DetailedComicEntity, ComicAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ComicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detailedComicEntity = getItem(position)
        holder.bind(detailedComicEntity)
    }

    class ViewHolder(private val binding: ComicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(detailedComicEntity: DetailedComicEntity) {
            binding.comicImage.loadUrl(detailedComicEntity.thumbnail)
            binding.comicTitle.text =
                detailedComicEntity.title ?: binding.root.context.getString(R.string.content_not_available)
        }
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<DetailedComicEntity>() {
    override fun areItemsTheSame(oldItem: DetailedComicEntity, newItem: DetailedComicEntity): Boolean =
        oldItem.resourceUri == newItem.resourceUri

    override fun areContentsTheSame(oldItem: DetailedComicEntity, newItem: DetailedComicEntity): Boolean =
        oldItem == newItem
}
