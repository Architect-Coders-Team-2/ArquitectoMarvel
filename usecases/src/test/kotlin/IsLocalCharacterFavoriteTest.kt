import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.usecases.IsLocalCharacterFavorite
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class IsLocalCharacterFavoriteTest {

    @Mock
    lateinit var marvelRepository: MarvelRepository

    lateinit var isLocalCharacterFavorite: IsLocalCharacterFavorite

    @Before
    fun setUp() {
        isLocalCharacterFavorite = IsLocalCharacterFavorite(marvelRepository)
    }

    @Test
    fun `Confirm if isLocalCharacterFavorite checks if character is favorite`() {
        whenever(isLocalCharacterFavorite.invoke(1)).thenReturn(1)
        val result = isLocalCharacterFavorite.invoke(1)
        assertEquals(result, 1)
    }
}
