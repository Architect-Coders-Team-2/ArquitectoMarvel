package com.architectcoders.arquitectomarvel.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.architectcoders.arquitectomarvel.data.database.HeroEntity
import com.architectcoders.arquitectomarvel.ui.common.REQUEST_LIMIT
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.usecases.*

class MainViewModel(
    getRemoteCharacters: GetRemoteCharacters,
    deleteAllLocalCharacters: DeleteAllLocalCharacters,
    insertAllLocalCharacters: InsertAllLocalCharacters,
    getLastTimeStampFromCharacterEntity: GetLastTimeStampFromCharacterEntity,
    getPagingSourceFromCharacterEntity: GetPagingSourceFromCharacterEntity,
    getLocalCharactersCount: GetLocalCharactersCount
) : ViewModel() {
    @ExperimentalPagingApi
    val pager = Pager(
        config = PagingConfig(pageSize = REQUEST_LIMIT / 2),
        remoteMediator =
        CharacterRemoteMediator(
            getRemoteCharacters,
            deleteAllLocalCharacters,
            insertAllLocalCharacters,
            getLastTimeStampFromCharacterEntity,
            getLocalCharactersCount
        )
    ) {
        getPagingSourceFromCharacterEntity.invoke() as PagingSource<Int, HeroEntity>
    }.flow.cachedIn(viewModelScope)
}
