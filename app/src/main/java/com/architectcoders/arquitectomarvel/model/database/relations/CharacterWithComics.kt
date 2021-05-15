package com.architectcoders.arquitectomarvel.model.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.architectcoders.arquitectomarvel.model.database.CharacterEntity
import com.architectcoders.arquitectomarvel.model.database.ComicEntity

data class CharacterWithComics(
    @Embedded val characterEntity: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "resourceUri"
    )
    val detailedComicEntity: List<ComicEntity>
)
