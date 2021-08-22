import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.DeleteLocalFavoriteCharacter
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DeleteLocalFavoriteCharacterTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var deleteLocalFavoriteCharacter: DeleteLocalFavoriteCharacter

    @Before
    fun setUp() {
        deleteLocalFavoriteCharacter = DeleteLocalFavoriteCharacter(marvelRepository)
    }

    @Test
    fun `verify if deleteLocalFavoriteCharacter is invoked`(): Unit = runBlocking {
        deleteLocalFavoriteCharacter.invoke(mockedCharacter)
        verify(marvelRepository).deleteLocalFavoriteCharacter(mockedCharacter)
    }
}
