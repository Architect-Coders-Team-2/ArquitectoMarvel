import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetLocalFavoriteCharacters
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetLocalFavoriteCharactersTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var getLocalFavoriteCharacters: GetLocalFavoriteCharacters

    @Before
    fun setUp() {
        getLocalFavoriteCharacters = GetLocalFavoriteCharacters(marvelRepository)
    }

    @Test
    fun `Confirm if getLocalFavoriteCharacters should retrieve the favorite characters`(): Unit = runBlocking {
        val characterList = listOf(mockedCharacter)
        whenever(marvelRepository.getLocalFavoriteCharacters()).thenReturn(characterList)
        val result = getLocalFavoriteCharacters.invoke(Unit)
        assertEquals(result, characterList)
    }
}
