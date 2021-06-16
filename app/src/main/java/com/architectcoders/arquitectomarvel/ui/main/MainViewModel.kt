package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.architectcoders.arquitectomarvel.ui.main.pagination.ResultPagingSource
import com.architectcoders.usecases.GetCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
private val getCharacters: GetCharacters
) : ViewModel() {
    val pager = Pager(
        config = PagingConfig(pageSize = 18),
        pagingSourceFactory = { ResultPagingSource(getCharacters) }
    ).flow.cachedIn(viewModelScope)
}
