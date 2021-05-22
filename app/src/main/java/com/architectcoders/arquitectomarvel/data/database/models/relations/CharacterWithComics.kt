package com.architectcoders.arquitectomarvel.data.database.models.relations

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Relation
import com.architectcoders.arquitectomarvel.data.database.models.CharacterEntity
import com.architectcoders.arquitectomarvel.data.database.models.ComicEntity

data class CharacterWithComics(
    @Embedded val characterEntity: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "resourceUri"
    )
    val comicEntity: List<ComicEntity>
)
