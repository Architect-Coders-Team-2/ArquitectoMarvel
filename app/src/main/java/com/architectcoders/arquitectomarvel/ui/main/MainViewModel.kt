package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.architectcoders.arquitectomarvel.ui.common.REQUEST_LIMIT
import com.architectcoders.arquitectomarvel.ui.main.pagination.ResultPagingSource
import com.architectcoders.usecases.GetRemoteCharacters

class MainViewModel(private val getRemoteCharacters: GetRemoteCharacters) : ViewModel() {
    val pager = Pager(
        config = PagingConfig(pageSize = REQUEST_LIMIT / 2),
        pagingSourceFactory = { ResultPagingSource(getRemoteCharacters) }
    ).flow.cachedIn(viewModelScope)
}
