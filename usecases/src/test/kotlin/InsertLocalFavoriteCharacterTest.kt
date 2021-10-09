import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.InsertLocalFavoriteCharacter
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class InsertLocalFavoriteCharacterTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var insertLocalFavoriteCharacter: InsertLocalFavoriteCharacter

    @Before
    fun setUp() {
        insertLocalFavoriteCharacter = InsertLocalFavoriteCharacter(marvelRepository)
    }

    @Test
    fun `verify insertLocalFavoriteCharacter is called`(): Unit = runBlocking {
        insertLocalFavoriteCharacter.invoke(mockedCharacter)
        verify(marvelRepository, times(1)).insertLocalFavoriteCharacter(mockedCharacter)
    }
}
