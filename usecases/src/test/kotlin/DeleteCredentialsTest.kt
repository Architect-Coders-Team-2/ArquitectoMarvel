import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.DeleteCredentials
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DeleteCredentialsTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var deleteCredentials: DeleteCredentials

    @Before
    fun setUp() {
        deleteCredentials = DeleteCredentials(marvelRepository)
    }

    @Test
    fun `verify if deleteCredentials is invoked`(): Unit = runBlocking {
        deleteCredentials.invoke(Unit)
        verify(marvelRepository).deleteCredentials()
    }
}
