import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.DeleteAllLocalCharacters
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DeleteAllLocalCharactersTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var deleteAllLocalCharacters: DeleteAllLocalCharacters

    @Before
    fun setUp() {
        deleteAllLocalCharacters = DeleteAllLocalCharacters(marvelRepository)
    }

    @Test
    fun `verify if deleteAllLocalCharacters is invoked`(): Unit = runBlocking {
        deleteAllLocalCharacters.invoke(Unit)
        verify(marvelRepository).deleteAllLocalCharacters()
    }
}
