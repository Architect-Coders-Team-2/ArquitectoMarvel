package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.architectcoders.arquitectomarvel.data.database.CharacterEntity
import com.architectcoders.arquitectomarvel.ui.common.REQUEST_LIMIT
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.usecases.*

class MainViewModel(
    getRemoteCharacters: GetRemoteCharacters,
    deleteAllCharacters: DeleteAllCharacters,
    insertAllCharacters: InsertAllCharacters,
    getLastTimeStamp: GetLastTimeStamp,
    getPagingSource: GetPagingSource,
    getStoredCharactersCount: GetStoredCharactersCount
) : ViewModel() {
    @ExperimentalPagingApi
    val pager = Pager(
        config = PagingConfig(pageSize = REQUEST_LIMIT / 2),
        remoteMediator =
        CharacterRemoteMediator(
            getRemoteCharacters,
            deleteAllCharacters,
            insertAllCharacters,
            getLastTimeStamp,
            getStoredCharactersCount
        )
    ) {
        getPagingSource.invoke() as PagingSource<Int, CharacterEntity>
    }.flow.cachedIn(viewModelScope)
}
