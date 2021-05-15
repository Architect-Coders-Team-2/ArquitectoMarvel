package com.architectcoders.arquitectomarvel.data.local

import com.architectcoders.arquitectomarvel.data.local.entities.toCharacterEntity
import com.architectcoders.module.data.sources.LocalDataSource
import com.architectcoders.module.domain.models.Characters.ResultCharacters
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val dao: ResultDao) : LocalDataSource {

    override suspend fun insertFavoriteCharacter(toCharacterEntity: ResultCharacters) {
        dao.insertFavoriteCharacter(toCharacterEntity.toCharacterEntity())
    }

    override suspend fun deleteFavoriteCharacter(favouriteCharacter: ResultCharacters) {
        dao.deleteFavoriteCharacter(favouriteCharacter.toCharacterEntity())
    }

    override suspend fun isCharacterFavorite(characterId: Int): Boolean {
        return dao.isCharacterFavorite(characterId) != null
    }
}