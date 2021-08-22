import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetLastTimeStampFromCharacterEntity
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetLastTimeStampFromCharacterEntityTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var getLastTimeStampFromCharacterEntity: GetLastTimeStampFromCharacterEntity

    @Before
    fun setUp() {
        getLastTimeStampFromCharacterEntity = GetLastTimeStampFromCharacterEntity(marvelRepository)
    }

    @Test
    fun `Confirm if getLastTimeStampFromCharacterEntity returns the last character timestamp`(): Unit = runBlocking {
        whenever(marvelRepository.getLastTimeStampFromCharacterEntity()).thenReturn(1L)
        val result = getLastTimeStampFromCharacterEntity.invoke(Unit)
        assertEquals(result, 1L)
    }
}
