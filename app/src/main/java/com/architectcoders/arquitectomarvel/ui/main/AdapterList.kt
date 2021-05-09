package com.architectcoders.arquitectomarvel.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.data.mappers.ResultUI
import com.architectcoders.arquitectomarvel.data.mappers.toResultUIList
import com.architectcoders.arquitectomarvel.databinding.HeroItemBinding
import com.architectcoders.arquitectomarvel.model.loadUrl
import com.architectcoders.module.usescases.UseCaseGetCharactersRemote
import retrofit2.HttpException
import java.io.IOException

class AdapterList(private val listener: (ResultUI, View) -> Unit) :
    PagingDataAdapter<ResultUI, AdapterList.HeroViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ResultUI>() {
        override fun areItemsTheSame(oldItem: ResultUI, newItem: ResultUI) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ResultUI, newItem: ResultUI) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val binding = HeroItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val result = getItem(position)
        if (result != null) {
            holder.bind(result)
            holder.itemView.setOnClickListener {
                listener(result, it)
            }
        }

    }

    inner class HeroViewHolder(private val binding: HeroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultUI: ResultUI) {
            binding.heroText.text = resultUI.name
            binding.heroImage.loadUrl(
                resultUI.thumbnail.path,
                resultUI.thumbnail.extension
            )
        }
    }
}