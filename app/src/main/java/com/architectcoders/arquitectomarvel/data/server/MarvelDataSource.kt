package com.architectcoders.arquitectomarvel.data.server

import com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelCharacters.toLocalCharacters
import com.architectcoders.arquitectomarvel.data.server.uiEntities.marvelComics.toLocalComic
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.characters.Characters
import com.architectcoders.domain.comics.Comic

class MarvelDataSource : RemoteDataSource {
    override suspend fun getCharacters(
        ts: Long,
        publicKey: String,
        hash: String,
        offset: Int
    ): Characters =
        MarvelApiRest.service.getCharacters(ts, publicKey, hash, offset).toLocalCharacters

    override suspend fun getCharacterById(
        characterId: Int,
        ts: Long,
        publicKey: String,
        hash: String
    ): Characters =
        MarvelApiRest.service.getCharacterById(characterId, ts, publicKey, hash).toLocalCharacters

    override suspend fun getComics(
        characterId: Int,
        ts: Long,
        publicKey: String,
        hash: String
    ): Comic? =
        MarvelApiRest.service.getComics(characterId, ts, publicKey, hash)?.toLocalComic
}
