import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.InsertRemoteComicsForLocalCharacter
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class InsertRemoteComicsForLocalCharacterTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var insertRemoteComicsForLocalCharacter: InsertRemoteComicsForLocalCharacter

    @Before
    fun setUp() {
        insertRemoteComicsForLocalCharacter = InsertRemoteComicsForLocalCharacter(marvelRepository)
    }

    @Test
    fun `verify insertRemoteComicsForLocalCharacter is invoked`(): Unit = runBlocking {
        val map = mapOf("CHARACTER_ID" to 1, "COMICS" to listOf(mockedComic))
        insertRemoteComicsForLocalCharacter.invoke(map)
        verify(marvelRepository).insertRemoteComicsForLocalCharacter(map)
    }
}
