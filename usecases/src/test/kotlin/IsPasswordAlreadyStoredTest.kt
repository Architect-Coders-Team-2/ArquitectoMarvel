import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.IsPasswordAlreadyStored
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class IsPasswordAlreadyStoredTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var isPasswordAlreadyStored: IsPasswordAlreadyStored

    @Before
    fun setUp() {
        isPasswordAlreadyStored = IsPasswordAlreadyStored(marvelRepository)
    }

    @Test
    fun `Confirm if the password is already stored`(): Unit = runBlocking {
        whenever(isPasswordAlreadyStored.invoke(Unit)).thenReturn(true)
        val result = isPasswordAlreadyStored.invoke(Unit)
        assertEquals(result, true)
    }
}
