package com.architectcoders.arquitectomarvel.ui.main.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.architectcoders.arquitectomarvel.data.database.CharacterEntity
import com.architectcoders.arquitectomarvel.ui.common.EMPTY_RESPONSE
import com.architectcoders.arquitectomarvel.ui.common.REQUEST_LIMIT
import com.architectcoders.usecases.*
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@ExperimentalPagingApi
class CharacterRemoteMediator(
    private val getRemoteCharacters: GetRemoteCharacters,
    private val deleteAllCharacters: DeleteAllCharacters,
    private val insertAllCharacters: InsertAllCharacters,
    private val getLastTimeStamp: GetLastTimeStamp,
    private val getStoredCharactersCount: GetStoredCharactersCount
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeOut = TimeUnit.DAYS.toMillis(1)
        val lastTimeStamp = getLastTimeStamp.invoke(Unit) ?: -1L
        return if (lastTimeStamp != -1L && System.currentTimeMillis() - lastTimeStamp >= cacheTimeOut) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {

            val nextPageNumber = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.pageNumber?.plus(1) ?: 0
            }
            val response = getRemoteCharacters.invoke(nextPageNumber * REQUEST_LIMIT)
            val characterList = response.characterData?.results
            if (characterList.isNullOrEmpty()) {
                if (response.characterData?.total == getStoredCharactersCount.invoke(Unit)) {
                    MediatorResult.Success(endOfPaginationReached = true)
                } else {
                    MediatorResult.Error(Exception(EMPTY_RESPONSE))
                }
            } else {
                if (loadType == LoadType.REFRESH) {
                    deleteAllCharacters.invoke(Unit)
                }
                characterList.map { it.pageNumber = nextPageNumber }
                insertAllCharacters.invoke(characterList)
                MediatorResult.Success(endOfPaginationReached = characterList.size < state.config.pageSize)
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
