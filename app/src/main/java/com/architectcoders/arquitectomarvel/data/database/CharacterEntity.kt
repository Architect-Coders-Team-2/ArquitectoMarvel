package com.architectcoders.arquitectomarvel.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.characters.Result
import com.architectcoders.domain.characters.Thumbnail

@Entity
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val resourceURI: String?,
    val comicCollectionUri: String?,
    val comicListAvailable: Int?,
    val insertDate: Long?
)

val List<Result>.toCharacterEntityList: List<CharacterEntity>
    get() = map { it.toCharacterEntity }

val Result.toCharacterEntity: CharacterEntity
    get() = CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        resourceURI = resourceURI,
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = if (comicListAvailable) 1 else 0,
        insertDate = System.currentTimeMillis()
    )

val List<CharacterEntity>.toDomainCharacterList: List<Result>
    get() = map { it.toDomainCharacter }

val CharacterEntity.toDomainCharacter: Result
    get() = Result(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail?.split(".")?.let { Thumbnail(it[0], it[1]) },
        resourceURI = resourceURI,
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = comicListAvailable != null,
        insertDate = insertDate
    )
