import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetLocalCharactersCount
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetLocalCharactersCountTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var getLocalCharactersCount: GetLocalCharactersCount

    @Before
    fun setUp() {
        getLocalCharactersCount = GetLocalCharactersCount(marvelRepository)
    }

    @Test
    fun `verify if getLocalCharactersCount is invoked`(): Unit = runBlocking {
        whenever(marvelRepository.getLocalCharactersCount()).thenReturn(1)
        val result = getLocalCharactersCount.invoke(Unit)
        assertEquals(result, 1)
    }
}
