import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.IsRecoveryHintCorrect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class IsRecoveryHintCorrectTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var isRecoveryHintCorrect: IsRecoveryHintCorrect

    @Before
    fun setUp() {
        isRecoveryHintCorrect = IsRecoveryHintCorrect(marvelRepository)
    }

    @Test
    fun `Confirm if the stored hint is correct`(): Unit = runBlocking {
        whenever(isRecoveryHintCorrect.invoke("hello")).thenReturn(true)
        val result = isRecoveryHintCorrect.invoke("hello")
        assertEquals(result, true)
    }
}
