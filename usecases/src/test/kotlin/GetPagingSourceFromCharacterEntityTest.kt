import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.GetPagingSourceFromCharacterEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class GetPagingSourceFromCharacterEntityTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var getPagingSourceFromCharacterEntity: GetPagingSourceFromCharacterEntity

    @Before
    fun setUp() {
        getPagingSourceFromCharacterEntity = GetPagingSourceFromCharacterEntity(marvelRepository)
    }

    @Test
    fun `verify if getPagingSourceFromCharacterEntity is invoked`(): Unit = runBlocking {
        getPagingSourceFromCharacterEntity.invoke()
        verify(marvelRepository, times(1)).getPagingSourceFromCharacterEntity()
    }
}
