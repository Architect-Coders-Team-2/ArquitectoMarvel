import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.IsPasswordCorrect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class IsPasswordCorrectTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var isPasswordCorrect: IsPasswordCorrect

    @Before
    fun setUp() {
        isPasswordCorrect = IsPasswordCorrect(marvelRepository)
    }

    @Test
    fun `Confirm if the stored password is correct`(): Unit = runBlocking {
        whenever(isPasswordCorrect.invoke("a")).thenReturn(true)
        val result = isPasswordCorrect.invoke("a")
        assertEquals(result, true)
    }
}
