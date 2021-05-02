package com.architectcoders.arquitectomarvel.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.databinding.HeroItemBinding
import com.architectcoders.arquitectomarvel.model.Repository
import com.architectcoders.arquitectomarvel.model.characters.Result
import com.architectcoders.arquitectomarvel.model.loadUrl
import retrofit2.HttpException
import java.io.IOException

class AdapterList(private val listener: (Result, View) -> Unit) :
    PagingDataAdapter<Result, AdapterList.HeroViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem
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

        fun bind(mediaService: Result) {
            binding.heroText.text = mediaService.name
            binding.heroImage.loadUrl(
                mediaService.thumbnail?.path,
                mediaService.thumbnail?.extension
            )
        }
    }
}

const val INICIAL = 36
class ResultPagingSource(
    val repository: Repository
) : PagingSource<Int, Result>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Result> {
        return try {
            val offset = params.key ?: INICIAL
            val response = repository.getCharactersRemote(offset)
            val results = response.characterData?.results!!
            LoadResult.Page(
                data = results,
//                prevKey = if (offset == INICIAL) null else offset + INICIAL,
                prevKey = null, // Only paging forward.
                nextKey = if (results.isEmpty()) null else offset + INICIAL
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(INICIAL) ?: anchorPage?.nextKey?.minus(INICIAL)
        }
    }
}