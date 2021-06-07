package com.architectcoders.arquitectomarvel.data.server.uiEntities.character

import com.architectcoders.arquitectomarvel.data.server.uiEntities.ThumbnailApi
import com.architectcoders.arquitectomarvel.data.server.uiEntities.toLocalThumbnail
import com.architectcoders.domain.character.Character
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterApi(
    @Json(name = "id")
    val id: Int = -1,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "thumbnail")
    val thumbnailApi: ThumbnailApi? = null,
    @Json(name = "resourceURI")
    val resourceURI: String? = null,
    @Json(name = "comics")
    val comicsMs: CharacterComicApi = CharacterComicApi()
)

val List<CharacterApi>.toLocalListCharacters: List<Character>
    get() = map { it.toLocalMarvelCharacter }

val CharacterApi.toLocalMarvelCharacter: Character
    get() = Character(
        id,
        name,
        description,
        thumbnailApi?.toLocalThumbnail,
        comicsMs.collectionURI,
        comicsMs.available != null,
        null,
        null
    )
