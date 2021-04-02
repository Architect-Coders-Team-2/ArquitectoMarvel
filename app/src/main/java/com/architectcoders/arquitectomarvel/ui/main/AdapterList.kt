package com.architectcoders.arquitectomarvel.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.databinding.HeroItemBinding
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.loadUrl

class AdapterList(val clickListener: ClickListener) :
    ListAdapter<Result, AdapterList.HeroViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val binding = HeroItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HeroViewHolder(private val binding: HeroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaService: Result) {
            binding.heroText.text = mediaService.name
            binding.heroImage.loadUrl(
                mediaService.thumbnail?.path,
                mediaService.thumbnail?.extension
            )
            binding.root.setOnClickListener {
                clickListener.onClick(mediaService)
            }
        }
    }
}