import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.Thumbnail
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.character.CharacterData
import com.architectcoders.domain.character.CharactersPayload
import com.architectcoders.domain.comic.Comic
import com.architectcoders.domain.comic.ComicData
import com.architectcoders.domain.comic.ComicsPayload
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MarvelRepositoryTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var localDataSource: LocalDataSource

    private val mockedCharacter = Character(
        id = 1011334,
        name = "3-D Man",
        description = "He loves 3-D movies!",
        thumbnail = Thumbnail(
            path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784",
            extension = "jpg"
        ),
        comicCollectionUri = "http://gateway.marvel.com/v1/public/characters/1011334",
        comicListAvailable = false,
        pageNumber = null,
        insertDate = null
    )

    private val mockedCharacterData = CharacterData(
        offset = 0,
        limit = 20,
        total = 20,
        count = 20,
        characters = listOf(mockedCharacter)
    )

    private val mockedCharactersPayload = CharactersPayload(
        code = 200,
        status = "Ok",
        characterData = mockedCharacterData
    )

    private val mockedComic = Comic(
        id = 22506,
        title = "Avengers: The Initiative (2007) #19",
        description = "Join 3-D MAN, CLOUD 9, KOMODO, HARDBALL, and heroes around America in " +
                "the battle that will decide the fate of the planet and the future of the " +
                "Initiative program. Will the Kill Krew Army win the day?",
        pageCount = 32,
        resourceURI = "http://gateway.marvel.com/v1/public/comics/22506",
        thumbnail = Thumbnail("http://i.annihil.us/u/prod/marvel/i/mg/d/03/58dd080719806", "jpg"),
        images = listOf(
            Thumbnail(
                "http://i.annihil.us/u/prod/marvel/i/mg/d/03/58dd080719806",
                "jpg"
            )
        ),
        insertDate = null
    )

    private val mockedComicData = ComicData(
        count = 1,
        total = 1,
        limit = 20,
        offset = 0,
        comics = listOf(mockedComic)
    )

    private val mockedComicsPayload = ComicsPayload(
        code = 200,
        comicData = mockedComicData
    )

    lateinit var marvelRepository: MarvelRepository

    @Before
    fun setUp() {
        marvelRepository = MarvelRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `Confirm if getRemoteCharacters gets characters from API`(): Unit = runBlocking {
        whenever(remoteDataSource.getRemoteCharacters(0)).thenReturn(mockedCharactersPayload)
        val result = marvelRepository.getRemoteCharacters(0)
        assertEquals(mockedCharactersPayload, result)
    }

    @Test
    fun `verify if getLocalCharactersCount is invoked`(): Unit = runBlocking {
        marvelRepository.getLocalCharactersCount()
        verify(localDataSource).getLocalCharactersCount()
    }

    @Test
    fun `Confirm if getLocalCharacterById retrieves a character from the database`(): Unit =
        runBlocking {
            whenever(localDataSource.getLocalCharacterById(1)).thenReturn(mockedCharacter)
            val result = marvelRepository.getLocalCharacterById(1)
            assertEquals(mockedCharacter, result)
        }

    @Test
    fun `Confirm if isLocalCharacterFavorite checks if character is favorite`() {
        whenever(localDataSource.isLocalCharacterFavorite(1)).thenReturn(1)
        val result = marvelRepository.isLocalCharacterFavorite(1)
        assertEquals(result, 1)
    }

    @Test
    fun `Confirm if getRemoteComicsFromCharacterId obtains remote comics from character id`(): Unit =
        runBlocking {
            whenever(remoteDataSource.getRemoteComics(1)).thenReturn(mockedComicsPayload)
            val result = marvelRepository.getRemoteComicsFromCharacterId(1)
            assertEquals(result, mockedComicsPayload)
        }

    @Test
    fun `verify if insertAllLocalCharacters is invoked`(): Unit = runBlocking {
        val characterList = listOf(mockedCharacter)
        marvelRepository.insertAllLocalCharacters(characterList)
        verify(localDataSource).insertAllLocalCharacters(characterList)
    }

    @Test
    fun `Confirm if getLocalFavoriteCharacters should retrieve the favorite characters`(): Unit =
        runBlocking {
            val characterList = listOf(mockedCharacter)
            whenever(localDataSource.getLocalFavoriteCharacters()).thenReturn(characterList)
            val result = marvelRepository.getLocalFavoriteCharacters()
            assertEquals(result, characterList)
        }

    @Test
    fun `verify if insertLocalFavoriteCharacter is invoked`(): Unit = runBlocking {
        marvelRepository.insertLocalFavoriteCharacter(mockedCharacter)
        verify(localDataSource).insertLocalFavoriteCharacter(mockedCharacter)
    }

    @Test
    fun `verify if deleteAllLocalCharacters is invoked`(): Unit = runBlocking {
        marvelRepository.deleteAllLocalCharacters()
        verify(localDataSource).deleteAllLocalCharacters()
    }

    @Test
    fun `verify if deleteLocalFavoriteCharacter is invoked`(): Unit = runBlocking {
        marvelRepository.deleteLocalFavoriteCharacter(mockedCharacter)
        verify(localDataSource).deleteLocalFavoriteCharacter(mockedCharacter)
    }

    @Test
    fun `Confirm if getLastTimeStampFromCharacterEntity returns the last character timestamp`(): Unit =
        runBlocking {
            val timestamp = 1L
            whenever(localDataSource.getLastTimeStampFromCharacterEntity()).thenReturn(timestamp)
            val result = marvelRepository.getLastTimeStampFromCharacterEntity()
            assertEquals(result, timestamp)
        }

    @Test
    fun `verify if getPagingSourceFromCharacterEntity is invoked`(): Unit = runBlocking {
        marvelRepository.getPagingSourceFromCharacterEntity()
        verify(localDataSource).getPagingSourceFromCharacterEntity()
    }

    @Test
    fun `verify if fetchComicsForCharacter is invoked`(): Unit = runBlocking {
        val map = mapOf("CHARACTER_ID" to 1, "COMICS" to listOf(mockedComic))
        marvelRepository.insertRemoteComicsForLocalCharacter(map)
        verify(localDataSource).insertRemoteComicsForLocalCharacter(map)
    }

    @Test
    fun `Confirm if getComicsForCharacter retrieves a list of comics by character id`(): Unit =
        runBlocking {
            val comicList = listOf(mockedComic)
            whenever(localDataSource.getComicsForCharacter(1)).thenReturn(comicList)
            val result = marvelRepository.getComicsForCharacter(1)
            assertEquals(result, comicList)
        }

    @Test
    fun `Confirm if the password is already stored`(): Unit = runBlocking {
        whenever(localDataSource.isPasswordAlreadyStored()).thenReturn(true)
        val result = localDataSource.isPasswordAlreadyStored()
        assertEquals(result, true)
    }

    @Test
    fun `verify if savesCredentials is invoked`(): Unit = runBlocking {
        marvelRepository.saveCredentials("a", "b")
        verify(localDataSource).saveCredentials("a", "b")
    }

    @Test
    fun `verify if deleteCredentials is invoked`(): Unit = runBlocking {
        marvelRepository.deleteCredentials()
        verify(localDataSource).deleteCredentials()
    }

    @Test
    fun `Confirm if the stored password is correct`(): Unit = runBlocking {
        whenever(localDataSource.isPasswordCorrect("a")).thenReturn(true)
        val result = localDataSource.isPasswordCorrect("a")
        assertEquals(result, true)
    }

    @Test
    fun `Confirm if the stored hint is correct`(): Unit = runBlocking {
        whenever(localDataSource.isRecoveryHintCorrect("hello")).thenReturn(true)
        val result = localDataSource.isRecoveryHintCorrect("hello")
        assertEquals(result, true)
    }

    @Test
    fun `verify if deleteAllLocalFavoriteCharacter is invoked`(): Unit = runBlocking {
        marvelRepository.deleteAllLocalFavoriteCharacter()
        verify(localDataSource).deleteAllLocalFavoriteCharacter()
    }
}
