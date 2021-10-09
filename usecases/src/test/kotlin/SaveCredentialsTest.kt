import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.SaveCredentials
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class SaveCredentialsTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var saveCredentials: SaveCredentials

    @Before
    fun setUp() {
        saveCredentials = SaveCredentials(marvelRepository)
    }

    @Test
    fun `verify if savesCredentials is invoked`(): Unit = runBlocking {
        saveCredentials.invoke("a", "b")
        verify(marvelRepository).saveCredentials("a", "b")
    }
}
