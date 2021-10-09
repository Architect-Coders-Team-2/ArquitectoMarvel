package main

import androidx.paging.*
import com.architectcoders.arquitectomarvel.data.database.CharacterEntity
import com.architectcoders.arquitectomarvel.ui.main.pagination.CharacterRemoteMediator
import com.architectcoders.domain.character.CharacterData
import com.architectcoders.usecases.*
import kotlinx.coroutines.runBlocking
import mockedCharacter
import mockedCharactersPayload
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CharacterRemoteMediatorTest {

    @Mock
    private lateinit var getRemoteCharacters: GetRemoteCharacters

    @Mock
    private lateinit var insertAllLocalCharacters: InsertAllLocalCharacters

    @Mock
    private lateinit var deleteAllLocalCharacters: DeleteAllLocalCharacters

    @Mock
    private lateinit var getLastTimeStampFromCharacterEntity: GetLastTimeStampFromCharacterEntity

    @Mock
    private lateinit var getLocalCharactersCount: GetLocalCharactersCount

    @ExperimentalPagingApi
    private lateinit var remoteMediator: CharacterRemoteMediator

    private lateinit var pagingState: PagingState<Int, CharacterEntity>

    private val mockedCharacterList = listOf(
        mockedCharacter.copy(id = 1),
        mockedCharacter.copy(id = 2),
        mockedCharacter.copy(id = 3),
        mockedCharacter.copy(id = 4)
    )

    @ExperimentalPagingApi
    @Before
    fun setup() {
        remoteMediator = CharacterRemoteMediator(
            getRemoteCharacters,
            deleteAllLocalCharacters,
            insertAllLocalCharacters,
            getLastTimeStampFromCharacterEntity,
            getLocalCharactersCount
        )
        pagingState = PagingState(
            listOf(),
            null,
            PagingConfig(3),
            3
        )
    }

    @ExperimentalPagingApi
    @Test
    fun `refresh load returns success results when more data is present`(): Unit = runBlocking {
        whenever(getRemoteCharacters.invoke(0)).thenReturn(
            mockedCharactersPayload.copy(
                characterData = CharacterData(0, 4, 4, 4, mockedCharacterList)
            )
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @ExperimentalPagingApi
    @Test
    fun `refresh load success and endOfPagination when no more data`(): Unit = runBlocking {
        whenever(getRemoteCharacters.invoke(0)).thenReturn(
            mockedCharactersPayload.copy(
                characterData = CharacterData(0, 0, 0, 0, listOf())
            )
        )
        whenever(getLocalCharactersCount.invoke(Unit)).thenReturn(0)
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @ExperimentalPagingApi
    @Test
    fun `refresh load returns error result when error occurs`(): Unit = runBlocking {
        whenever(getRemoteCharacters.invoke(0)).thenReturn(
            mockedCharactersPayload.copy(
                characterData = CharacterData(0, 0, 0, 0, listOf())
            )
        )
        whenever(getLocalCharactersCount.invoke(Unit)).thenReturn(1)
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}
