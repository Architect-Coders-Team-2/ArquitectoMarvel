import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import com.architectcoders.arquitectomarvel.data.database.ComicEntity
import com.architectcoders.arquitectomarvel.data.database.toComicEntity
import com.architectcoders.arquitectomarvel.ui.detail.CharacterDetailViewModel
import com.architectcoders.arquitectomarvel.ui.detail.GetComicsInteractor
import com.architectcoders.arquitectomarvel.ui.detail.Resource
import com.architectcoders.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
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
    lateinit var observer: Observer<Resource<List<ComicEntity>>>

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
        characterDetailViewModel.comicResource.observeForever(observer)
        verify(observer, times(2)).onChanged(any())
    }

    @ExperimentalTime
    @Test
    fun `confirm if UiModel state is Loading`(): Unit = runBlocking {
        characterDetailViewModel.uiModel.test {
            assertEquals(awaitItem(), CharacterDetailViewModel.UiModel.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }
}
