import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetRemoteComicsFromCharacterId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetRemoteComicsFromCharacterIdTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId

    @Before
    fun setUp() {
        getRemoteComicsFromCharacterId = GetRemoteComicsFromCharacterId(marvelRepository)
    }

    @Test
    fun `Confirm if getRemoteComicsFromCharacterId obtains remote comics from character id`(): Unit = runBlocking {
        whenever(marvelRepository.getRemoteComicsFromCharacterId(1)).thenReturn(mockedComicsPayload)
        val result = getRemoteComicsFromCharacterId.invoke(1)
        assertEquals(result, mockedComicsPayload)
    }
}
