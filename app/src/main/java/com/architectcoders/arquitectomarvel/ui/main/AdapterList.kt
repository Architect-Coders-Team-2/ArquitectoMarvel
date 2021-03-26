package com.architectcoders.arquitectomarvel.ui.main

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.R
import com.architectcoders.arquitectomarvel.databinding.HeroItemBinding
import com.architectcoders.arquitectomarvel.model.characters.Item
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.loadUrl
import com.bumptech.glide.Glide

class AdapterList(val act: Activity, private val services: List<Result>?): RecyclerView.Adapter<AdapterList.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hero_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = services!!.get(position)
        holder.bind(item, act)
    }

    override fun getItemCount(): Int = services!!.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val binding = HeroItemBinding.bind(view)

        fun bind(mediaService: Result, activity: Activity){
            binding.heroText.text = mediaService.name
            binding.heroImage.loadUrl(mediaService.thumbnail!!.path, mediaService.thumbnail!!.extension)


         //   butDetail.setOnClickListener{
        //        activity.startActivity(Intent(activity, DetailActivity::class.java))
        //    }


        }


    }



}