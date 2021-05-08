package com.architectcoders.arquitectomarvel.model.mappers

import android.os.Parcelable
import com.architectcoders.arquitectomarvel.model.characters.Thumbnail
import com.architectcoders.arquitectomarvel.model.database.CharacterEntity
import kotlinx.parcelize.Parcelize
import com.architectcoders.arquitectomarvel.model.characters.Result as CharacterResult
import com.architectcoders.module.domain.remote_models.Characters.Result as ResultDomain

fun ResultDomain.mapperToResultUI(): ResultUI {
    return ResultUI(
        id = this.id,
        description = description ?: "",
        name = name ?: "",
        thumbnail = ThumbnailUI(
            path = this.thumbnail?.path,
            extension = this.thumbnail?.extension
        )
    )
}

fun List<ResultDomain>.toResultUIList(): List<ResultUI> = map { it.mapperToResultUI() }

@Parcelize
data class ResultUI(
    val id: Int,
    val description: String,
    val name: String,
    val thumbnail: ThumbnailUI
) : Parcelable

fun ResultUI.fromResultUItoCharacterResult(): CharacterResult =
    CharacterResult(
        id = id,
        name,
        description,
        thumbnail = Thumbnail(thumbnail.path, thumbnail.extension)
    )

@Parcelize
data class ThumbnailUI(
    val path: String?,
    val extension: String?
) : Parcelable

// mapper to characterEntity
fun ResultDomain.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}",
        comicCollectionUri = comics?.collectionURI?:"",
        comicListAvailable = comics?.available
    )
}