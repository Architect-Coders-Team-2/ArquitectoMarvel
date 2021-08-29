import com.architectcoders.domain.Thumbnail
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.character.CharacterData
import com.architectcoders.domain.character.CharactersPayload
import com.architectcoders.domain.comic.Comic
import com.architectcoders.domain.comic.ComicData
import com.architectcoders.domain.comic.ComicsPayload

val mockedCharacter = Character(
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

val mockedCharacterData = CharacterData(
    offset = 0,
    limit = 20,
    total = 20,
    count = 20,
    characters = listOf(mockedCharacter)
)

val mockedCharactersPayload = CharactersPayload(
    code = 200,
    status = "Ok",
    characterData = mockedCharacterData
)

val mockedComic = Comic(
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

val mockedComicData = ComicData(
    count = 1,
    total = 1,
    limit = 20,
    offset = 0,
    comics = listOf(mockedComic)
)

val mockedComicsPayload = ComicsPayload(
    code = 200,
    comicData = mockedComicData
)
