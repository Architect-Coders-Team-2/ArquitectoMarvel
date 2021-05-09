package com.architectcoders.arquitectomarvel.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.architectcoders.arquitectomarvel.data.local.entities.ItemComics
import com.architectcoders.arquitectomarvel.data.local.entities.ResultRoom
import com.architectcoders.arquitectomarvel.data.local.entities.toItems
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Comics
import com.architectcoders.arquitectomarvel.data.remote.models_moshi.characters.Result

data class ResultWithItemsComics(
    @Embedded val resultRoom: ResultRoom,
    @Relation(
        parentColumn = "comicsCollectionURI",
        entityColumn = "collectionURI"
    )
    val itemsComics: List<ItemComics>
)

val ResultWithItemsComics.toRemoteResult: Result
    get() = Result(
        id = this.resultRoom.id,
        name = resultRoom.name,
        thumbnail = resultRoom.thumbnail,
        comics = Comics(
            available = resultRoom.comicsAvailable,
            collectionURI = resultRoom.comicsCollectionURI,
            items = itemsComics.toItems
        )
    )

val List<ResultWithItemsComics>.toListResult
    get() = map { it.toRemoteResult }
