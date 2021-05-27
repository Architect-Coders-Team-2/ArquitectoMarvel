package com.architectcoders.arquitectomarvel.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architectcoders.domain.characters.Result
import com.architectcoders.domain.characters.Thumbnail
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val resourceURI: String?,
    val comicCollectionUri: String?,
    val comicListAvailable: Int?,
    val pageNumber: Int?,
    val insertDate: Long?
): Parcelable

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
        pageNumber = pageNumber,
        insertDate = System.currentTimeMillis()
    )

val List<CharacterEntity>.toDomainCharacterList: List<Result>
    get() = map { it.toDomainCharacter }

val CharacterEntity.toDomainCharacter: Result
    get() = Result(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail?.let {
            Thumbnail(
                it.substringBeforeLast("."),
                it.substringAfterLast(".")
            )
        },
        resourceURI = resourceURI,
        comicCollectionUri = comicCollectionUri,
        comicListAvailable = comicListAvailable != null,
        pageNumber = pageNumber,
        insertDate = insertDate
    )
