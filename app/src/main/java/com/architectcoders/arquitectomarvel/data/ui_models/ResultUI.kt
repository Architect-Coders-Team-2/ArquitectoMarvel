package com.architectcoders.arquitectomarvel.data.ui_models

import android.os.Parcelable
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Thumbnail
import kotlinx.parcelize.Parcelize
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Result as CharacterResult
import com.architectcoders.module.domain.models.Characters.ResultCharacters as ResultDomain

@Parcelize
data class ResultUI(
    val id: Int,
    val description: String,
    val name: String,
    val thumbnail: ThumbnailUI
) : Parcelable

@Parcelize
data class ThumbnailUI(
    val path: String?,
    val extension: String?
) : Parcelable

fun ResultDomain.mapperToResultUI(): ResultUI {
    return ResultUI(
        id = this.id,
        description = description ?: "",
        name = name ?: "",
        thumbnail = ThumbnailUI(
            path = this.thumbnailDomain?.path,
            extension = this.thumbnailDomain?.extension
        )
    )
}

fun List<ResultDomain>.toResultUIList(): List<ResultUI> = map { it.mapperToResultUI() }

fun ResultUI.fromResultUItoCharacterResult(): CharacterResult =
    CharacterResult(
        id = id,
        name,
        description,
        thumbnail = Thumbnail(thumbnail.path, thumbnail.extension)
    )