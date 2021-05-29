package com.architectcoders.arquitectomarvel.ui.main.pagging


import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.arquitectomarvel.databinding.ReposLoadStateFooterViewItemBinding

class LoadStateViewHolder(
    private val binding: ReposLoadStateFooterViewItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.errorMsg.isVisible = loadState is LoadState.Error
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.retryButton.also {
            it.setOnClickListener { retry() }
        }
    }
}