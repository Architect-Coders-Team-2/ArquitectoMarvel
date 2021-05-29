package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.*
import androidx.paging.*
import com.architectcoders.arquitectomarvel.ui.main.pagging.ResultPagingSource
import com.architectcoders.usecase.GetCharacters

class MainViewModel(private val getCharacters: GetCharacters) : ViewModel() {
    val pager = Pager(
        config = PagingConfig(pageSize = 18),
        pagingSourceFactory = { ResultPagingSource(getCharacters) }
    ).flow.cachedIn(viewModelScope)
}
