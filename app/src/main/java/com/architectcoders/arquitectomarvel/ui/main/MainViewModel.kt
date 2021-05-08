package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.architectcoders.arquitectomarvel.ui.main.pagination.ResultPagingSource
import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.usecases.GetCharacters

class MainViewModel(private val characterRepository: CharacterRepository) : ViewModel() {
    val pager = Pager(
        config = PagingConfig(pageSize = 18),
        pagingSourceFactory = { ResultPagingSource(GetCharacters(characterRepository)) }
    ).flow.cachedIn(viewModelScope)
}
