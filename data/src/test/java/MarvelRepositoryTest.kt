import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.Thumbnail
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.character.CharacterData
import com.architectcoders.domain.character.CharactersPayload
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MarvelRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    lateinit var marvelRepository: MarvelRepository

    @Before
    fun setUp() {
        marvelRepository = MarvelRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `getLocalCharacterById from local data source`() = runBlocking {
        val character = mockCharacter.copy(id = 1009144)
        whenever(localDataSource.getLocalCharacterById(1009144)).thenReturn(character)
        val result = marvelRepository.getLocalCharacterById(1009144)
        assertEquals(character, result)
    }


    @Test
    fun `insertLocalFavoriteCharacter save favorites character`() = runBlocking {
        val character = mockCharacter.copy(0)
        marvelRepository.insertLocalFavoriteCharacter(character)
        verify(localDataSource).insertLocalFavoriteCharacter(character)
    }

    @Test
    fun `get remote characteres`() = runBlocking {

        whenever(remoteDataSource.getRemoteCharacters(10)).thenReturn(mockCharactersPayload)
        val result = marvelRepository.getRemoteCharacters(10)

        assertEquals(mockCharactersPayload, result)

    }

    private val mockCharacter = Character(
        1,
        "Name",
        "Description",
        Thumbnail("http://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec", "jpg"),
        "http://gateway.marvel.com/v1/public/characters/1009144/comics",
        true,
        1,
        1627978661415
    )
    private val mockCharactersPayload = CharactersPayload(

        200,
        "done",
        CharacterData(
            10,
            10,
            1,
            1,
            listOf(mockCharacter)
        )
    )


    private val characterIdWithComics = 1017100


}