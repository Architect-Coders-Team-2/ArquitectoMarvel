package com.architectcoders.arquitectomarvel.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.architectcoders.arquitectomarvel.data.database.CharacterEntity
import com.architectcoders.arquitectomarvel.data.database.FavoriteComicEntity

data class CharacterWithComics(
    @Embedded val characterEntity: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "resourceUri"
    )
    val favoriteComicEntity: List<FavoriteComicEntity>
)
