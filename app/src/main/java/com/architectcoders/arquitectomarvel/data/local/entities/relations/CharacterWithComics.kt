package com.architectcoders.arquitectomarvel.data.local.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.architectcoders.arquitectomarvel.data.local.entities.CharacterEntity
import com.architectcoders.arquitectomarvel.data.local.entities.DetailedComicEntity

data class CharacterWithComics(
    @Embedded val characterEntity: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "resourceUri"
    )
    val detailedComicEntity: List<DetailedComicEntity>
)
