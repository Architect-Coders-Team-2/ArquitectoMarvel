import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetComicsForCharacter
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetComicsForCharacterTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var getComicsForCharacter: GetComicsForCharacter

    @Before
    fun setUp() {
        getComicsForCharacter = GetComicsForCharacter(marvelRepository)
    }

    @Test
    fun `Confirm if getComicsForCharacter retrieves a list of comics by character id`(): Unit = runBlocking {
        whenever(marvelRepository.getComicsForCharacter(1)).thenReturn(mockedCharacter)
        val result = getComicsForCharacter.invoke(1)
        assertEquals(result, mockedCharacter)
    }
}
