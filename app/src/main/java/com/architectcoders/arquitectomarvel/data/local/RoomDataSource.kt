package com.architectcoders.arquitectomarvel.data.local

import com.architectcoders.arquitectomarvel.data.local.entities.toCharacterEntity
import com.architectcoders.arquitectomarvel.data.local.entities.toDetailedComicEntity
import com.architectcoders.module.data.sources.LocalDataSource
import com.architectcoders.module.domain.models.Characters.ResultCharacters
import com.architectcoders.module.domain.models.Comics.DetailedComic

class RoomDataSource(resultDatabase: ResultDatabase) : LocalDataSource {

    private val dao: ResultDao = resultDatabase.resultDao

    override suspend fun insertFavoriteCharacter(toCharacterEntity: ResultCharacters) {
        dao.insertFavoriteCharacter(toCharacterEntity.toCharacterEntity())
    }

    override suspend fun insertFavoriteDetailedComic(comic: DetailedComic) {
        dao.insertFavoriteDetailedComic(comic.toDetailedComicEntity())
    }

    override suspend fun deleteFavoriteCharacter(favouriteCharacter: ResultCharacters) {
        dao.deleteFavoriteCharacter(favouriteCharacter.toCharacterEntity())
    }

    override suspend fun deleteFavoriteDetailedComic(comic: DetailedComic) {
        dao.deleteFavoriteDetailedComic(comic.toDetailedComicEntity())
    }

    override suspend fun isCharacterFavorite(characterId: Int): Boolean {
        return dao.isCharacterFavorite(characterId) != null
    }
}