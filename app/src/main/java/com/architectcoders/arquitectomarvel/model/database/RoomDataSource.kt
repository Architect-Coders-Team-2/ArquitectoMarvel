package com.architectcoders.arquitectomarvel.model.database

import com.architectcoders.arquitectomarvel.model.mappers.toCharacterEntity
import com.architectcoders.arquitectomarvel.model.mappers.toDetailedComicEntity
import com.architectcoders.module.data.LocalDataSource
import com.architectcoders.module.domain.local_models.DetailedComic
import com.architectcoders.module.domain.remote_models.Characters.Result

class RoomDataSource(resultDatabase: ResultDatabase) : LocalDataSource {

    private val dao: ResultDao = resultDatabase.resultDao

    override suspend fun insertFavoriteCharacter(toCharacterEntity: Result) {
        dao.insertFavoriteCharacter(toCharacterEntity.toCharacterEntity())
    }

    override suspend fun insertFavoriteDetailedComic(comic: DetailedComic) {
        dao.insertFavoriteDetailedComic(comic.toDetailedComicEntity())
    }

    override suspend fun deleteFavoriteCharacter(favouriteCharacter: Result) {
        dao.deleteFavoriteCharacter(favouriteCharacter.toCharacterEntity())
    }

    override suspend fun deleteFavoriteDetailedComic(comic: DetailedComic) {
        dao.deleteFavoriteDetailedComic(comic.toDetailedComicEntity())
    }

    override suspend fun isCharacterFavorite(characterId: Int): Int? {
        return dao.isCharacterFavorite(characterId)
    }
}