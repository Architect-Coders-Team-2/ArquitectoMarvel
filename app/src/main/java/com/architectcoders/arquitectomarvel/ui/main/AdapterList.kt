package com.architectcoders.arquitectomarvel.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.databinding.HeroItemBinding
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.loadUrl
import kotlin.properties.Delegates

class AdapterList(val clickListener: ClickListener) :
    RecyclerView.Adapter<AdapterList.HeroViewHolder>() {

    var services: List<Result> by Delegates.observable(emptyList()) {_, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val binding = HeroItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HeroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int = services.size

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