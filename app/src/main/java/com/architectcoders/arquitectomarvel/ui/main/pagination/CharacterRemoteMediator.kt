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
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRemoteMediator @Inject constructor(
    private val getRemoteCharacters: GetRemoteCharacters,
    private val deleteAllLocalCharacters: DeleteAllLocalCharacters,
    private val insertAllLocalCharacters: InsertAllLocalCharacters,
    private val getLastTimeStampFromCharacterEntity: GetLastTimeStampFromCharacterEntity,
    private val getLocalCharactersCount: GetLocalCharactersCount
) : RemoteMediator<Int, CharacterEntity>() {

    private lateinit var lastMediatorResult: MediatorResult

    override suspend fun initialize(): InitializeAction {
        val cacheTimeOut = TimeUnit.DAYS.toMillis(1)
        val lastTimeStamp = getLastTimeStampFromCharacterEntity.invoke(Unit) ?: -1L
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
        lastMediatorResult = try {
            val nextPageNumber = when (loadType) {
                LoadType.REFRESH -> {
                    if (::lastMediatorResult.isInitialized && lastMediatorResult is MediatorResult.Error) {
                        state.lastItemOrNull()?.pageNumber?.plus(1) ?: 0
                    } else {
                        0
                    }
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.pageNumber?.plus(1) ?: 0
            }
            val response = getRemoteCharacters.invoke(nextPageNumber.times(REQUEST_LIMIT))
            val characterList = response.characterData?.characters
            if (characterList.isNullOrEmpty()) {
                if (response.characterData?.total == getLocalCharactersCount.invoke(Unit)) {
                    MediatorResult.Success(endOfPaginationReached = true)
                } else {
                    MediatorResult.Error(Exception(EMPTY_RESPONSE))
                }
            } else {
                if (loadType == LoadType.REFRESH && nextPageNumber == 0) {
                    deleteAllLocalCharacters.invoke(Unit)
                }
                characterList.map { it.pageNumber = nextPageNumber }
                insertAllLocalCharacters.invoke(characterList)
                MediatorResult.Success(endOfPaginationReached = characterList.size < state.config.pageSize)
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
        return lastMediatorResult
    }
}
