import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.DeleteAllLocalFavoriteCharacter
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DeleteAllLocalFavoriteCharacterTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var deleteAllLocalFavoriteCharacter: DeleteAllLocalFavoriteCharacter

    @Before
    fun setUp() {
        deleteAllLocalFavoriteCharacter = DeleteAllLocalFavoriteCharacter(marvelRepository)
    }

    @Test
    fun `verify if deleteAllLocalCharacters is invoked`(): Unit = runBlocking {
        deleteAllLocalFavoriteCharacter.invoke(Unit)
        verify(marvelRepository).deleteAllLocalFavoriteCharacter()
    }
}
