import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.InsertAllLocalCharacters
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class InsertAllLocalCharactersTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var insertAllLocalCharacters: InsertAllLocalCharacters

    @Before
    fun setUp() {
        insertAllLocalCharacters = InsertAllLocalCharacters(marvelRepository)
    }

    @Test
    fun `verify if insertAllLocalCharacters is invoked`(): Unit = runBlocking {
        val characterList = listOf(mockedCharacter)
        insertAllLocalCharacters.invoke(characterList)
        verify(marvelRepository).insertAllLocalCharacters(characterList)
    }
}
