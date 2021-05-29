package com.architectcoders.arquitectomarvel.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.data.database.models.ComicEntity
import com.architectcoders.arquitectomarvel.databinding.ComicItemBinding
import com.architectcoders.arquitectomarvel.ui.common.loadUrl

class ComicAdapter : ListAdapter<ComicEntity, ComicAdapter.ViewHolder>(DiffUtilCallback) {

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
        fun bind(comicEntity: ComicEntity) {
            binding.comicImage.loadUrl(comicEntity.thumbnail)
            binding.comicTitle.text =
                comicEntity.title ?: binding.root.context.getString(R.string.content_not_available)
        }
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<ComicEntity>() {
    override fun areItemsTheSame(oldItem: ComicEntity, newItem: ComicEntity): Boolean =
        oldItem.resourceUri == newItem.resourceUri

    override fun areContentsTheSame(oldItem: ComicEntity, newItem: ComicEntity): Boolean =
        oldItem == newItem
}
