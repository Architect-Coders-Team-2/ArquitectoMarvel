package com.architectcoders.arquitectomarvel.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.ComicItemBinding
import com.architectcoders.arquitectomarvel.model.comics.Result
import com.architectcoders.arquitectomarvel.model.loadUrl

class ComicAdapter : ListAdapter<Result, ComicAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ComicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

    class ViewHolder(private val binding: ComicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.comicImage.loadUrl(
                result.thumbnail?.path,
                result.thumbnail?.extension
            )
            binding.comicTitle.text =
                result.title ?: binding.root.context.getString(R.string.content_not_available)
        }
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
        oldItem == newItem
}
