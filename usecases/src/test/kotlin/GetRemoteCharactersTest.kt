import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetRemoteCharacters
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetRemoteCharactersTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var getRemoteCharacters: GetRemoteCharacters

    @Before
    fun setUp() {
        getRemoteCharacters = GetRemoteCharacters(marvelRepository)
    }

    @Test
    fun `Confirm if getRemoteCharacters gets characters from API`(): Unit = runBlocking {
        whenever(marvelRepository.getRemoteCharacters(0)).thenReturn(mockedCharactersPayload)
        val result = getRemoteCharacters.invoke(0)
        assertEquals(result, mockedCharactersPayload)
    }
}
