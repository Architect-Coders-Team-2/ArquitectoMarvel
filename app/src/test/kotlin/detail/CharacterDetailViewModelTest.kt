package detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.architectcoders.arquitectomarvel.data.database.toComicEntity
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel
import com.architectcoders.arquitectomarvel.ui.detail.GetComicsInteractor
import com.architectcoders.arquitectomarvel.ui.detail.Resource
import com.architectcoders.usecases.*
import getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import mockedCharacter
import mockedComic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId

    @Mock
    private lateinit var insertRemoteComicsForLocalCharacter: InsertRemoteComicsForLocalCharacter

    @Mock
    private lateinit var getComicsForCharacter: GetComicsForCharacter

    @Mock
    private lateinit var getLocalCharacterById: GetLocalCharacterById

    @Mock
    private lateinit var isLocalCharacterFavorite: IsLocalCharacterFavorite

    @Mock
    private lateinit var insertLocalFavoriteCharacter: InsertLocalFavoriteCharacter

    @Mock
    private lateinit var deleteLocalFavoriteCharacter: DeleteLocalFavoriteCharacter

    private lateinit var getComicsInteractor: GetComicsInteractor

    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        getComicsInteractor = GetComicsInteractor(
            getRemoteComicsFromCharacterId,
            insertRemoteComicsForLocalCharacter,
            getComicsForCharacter
        )
        characterDetailViewModel = CharacterDetailViewModel(
            Dispatchers.Unconfined,
            mockedCharacter.id,
            getLocalCharacterById,
            isLocalCharacterFavorite,
            insertLocalFavoriteCharacter,
            deleteLocalFavoriteCharacter,
            getComicsInteractor
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `observing comicResource retrieves comics`(): Unit = runBlocking {
        val mockComicEntity = listOf(mockedComic.toComicEntity)
        whenever(getComicsForCharacter.invoke(mockedCharacter.id)).thenReturn(flowOf(mockComicEntity))
        val result = characterDetailViewModel.comicResource.getOrAwaitValue()
        assert(result is Resource.Loading)
        val result2 = characterDetailViewModel.comicResource.getOrAwaitValue()
        assert(result2 is Resource.Success)
        verify(getComicsForCharacter, times(2)).invoke(mockedCharacter.id)
    }

    @ExperimentalTime
    @Test
    fun `confirm if uiModel state is Loading`(): Unit = runBlocking {
        characterDetailViewModel.uiModel.test {
            assertEquals(awaitItem(), CharacterDetailViewModel.UiModel.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `confirm if uiModel state is SetUiDetails`(): Unit = runBlocking {
        whenever(getLocalCharacterById.invoke(mockedCharacter.id)).thenReturn(mockedCharacter)
        whenever(isLocalCharacterFavorite.invoke(mockedCharacter.id)).thenReturn(flowOf(1))
        characterDetailViewModel.uiModel.test {
            assert(awaitItem() is CharacterDetailViewModel.UiModel.SetUiDetails)
            cancelAndConsumeRemainingEvents()
        }
    }
}
