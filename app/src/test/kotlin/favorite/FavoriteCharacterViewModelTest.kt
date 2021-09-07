package favorite

import app.cash.turbine.test
import com.architectcoders.arquitectomarvel.data.database.FavoriteCharacterEntity
import com.architectcoders.arquitectomarvel.data.database.toFavoriteCharacterEntityList
import com.architectcoders.arquitectomarvel.ui.favorite.FavoriteCharacterViewModel
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import mockedCharacter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

@RunWith(MockitoJUnitRunner::class)
class FavoriteCharacterViewModelTest {

    @Mock
    private lateinit var getLocalFavoriteCharacters: GetLocalFavoriteCharacters

    private lateinit var favoriteViewModel: FavoriteCharacterViewModel

    @Before
    fun setUp() {
        favoriteViewModel = FavoriteCharacterViewModel(
            Dispatchers.Unconfined,
            getLocalFavoriteCharacters
        )
    }

    @ExperimentalTime
    @Test
    fun `confirm if uiModel state is Loading`(): Unit = runBlocking {
        favoriteViewModel.uiModel.test {
            assertEquals(awaitItem(), FavoriteCharacterViewModel.UiModel.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `confirm if uiModel state is RetrieveFavoriteCharacters`(): Unit = runBlocking {
        val mockedFavoriteCharacterList: List<FavoriteCharacterEntity> = listOf(
            mockedCharacter.copy(id = 1),
            mockedCharacter.copy(id = 2),
            mockedCharacter.copy(id = 3),
            mockedCharacter.copy(id = 4)
        ).toFavoriteCharacterEntityList
        val mockedFavoriteCharacterListFlow: Flow<List<FavoriteCharacterEntity>> = flowOf(
            mockedFavoriteCharacterList
        )
        whenever(getLocalFavoriteCharacters.invoke(Unit)).thenReturn(mockedFavoriteCharacterList)
        favoriteViewModel.uiModel.test {
            mockedFavoriteCharacterListFlow.test {
                assertEquals(
                    awaitItem(),
                    mockedFavoriteCharacterList
                )
                awaitComplete()
            }
            cancelAndConsumeRemainingEvents()
        }
    }
}
