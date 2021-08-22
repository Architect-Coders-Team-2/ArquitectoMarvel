import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetLocalCharacterById
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetLocalCharacterByIdTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var getLocalCharacterById: GetLocalCharacterById

    @Before
    fun setUp() {
        getLocalCharacterById = GetLocalCharacterById(marvelRepository)
    }

    @Test
    fun `Confirm if getLocalCharacterById retrieves a character from the database`(): Unit = runBlocking {
        whenever(marvelRepository.getLocalCharacterById(1)).thenReturn(mockedCharacter)
        val result = getLocalCharacterById.invoke(1)
        assertEquals(result, mockedCharacter)
    }
}
