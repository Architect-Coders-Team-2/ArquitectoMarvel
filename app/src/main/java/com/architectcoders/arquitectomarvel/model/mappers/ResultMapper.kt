package com.architectcoders.arquitectomarvel.model.mappers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.architectcoders.module.domain.remote_models.Characters.Result as ResultDomain

fun ResultDomain.mapperToResultUI(): ResultUI {
    return ResultUI(
        id = this.id ?: -1,
        description = description ?: "",
        name = name ?: "",
        thumbnail = ThumbnailUI(
            path = this.thumbnail.path,
            extension = this.thumbnail.extension
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

@Parcelize
data class ThumbnailUI(
    val path: String?,
    val extension: String?
) : Parcelable