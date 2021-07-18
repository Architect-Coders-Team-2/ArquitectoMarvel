package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.architectcoders.arquitectomarvel.data.database.CharacterEntity
import com.architectcoders.arquitectomarvel.ui.common.REQUEST_LIMIT
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @ExperimentalPagingApi
@Inject constructor(
    getPagingSourceFromCharacterEntity: GetPagingSourceFromCharacterEntity,
    characterRemoteMediator: CharacterRemoteMediator
) : ViewModel() {

    @ExperimentalPagingApi
    @Suppress("UNCHECKED_CAST")
    val pager = Pager(
        config = PagingConfig(pageSize = REQUEST_LIMIT / 2),
        remoteMediator = characterRemoteMediator
    ) {
        getPagingSourceFromCharacterEntity.invoke() as PagingSource<Int, CharacterEntity>
    }.flow.cachedIn(viewModelScope)
}
