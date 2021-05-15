package com.architectcoders.arquitectomarvel.model.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.architectcoders.domain.characters.Result
import com.architectcoders.arquitectomarvel.model.database.ItemComics
import com.architectcoders.arquitectomarvel.model.database.ResultRoom
import com.architectcoders.arquitectomarvel.model.database.toItems
import com.architectcoders.domain.characters.Comics

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
