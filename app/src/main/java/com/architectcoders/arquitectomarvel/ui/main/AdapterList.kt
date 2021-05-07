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
import com.architectcoders.arquitectomarvel.model.loadUrl
import com.architectcoders.arquitectomarvel.model.mappers.ResultUI
import com.architectcoders.arquitectomarvel.model.mappers.toResultUIList
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

const val INITIAL = 36
class ResultUIPagingSource(
    private val useCaseGetCharactersRemote: UseCaseGetCharactersRemote
) : PagingSource<Int, ResultUI>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ResultUI> {
        return try {
            val offset = params.key ?: INITIAL
            val response = useCaseGetCharactersRemote.invoke(offset)
            val results = response.data?.results!!.toResultUIList()
            LoadResult.Page(
                data = results,
                prevKey = null, // Only paging forward.
                nextKey = if (results.isEmpty()) null else offset + INITIAL
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultUI>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(INITIAL) ?: anchorPage?.nextKey?.minus(INITIAL)
        }
    }
}