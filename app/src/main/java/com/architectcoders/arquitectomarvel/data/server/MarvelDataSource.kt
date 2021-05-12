package com.architectcoders.arquitectomarvel.data.server

import com.architectcoders.arquitectomarvel.data.server.models.character.toDomainCharacters
import com.architectcoders.arquitectomarvel.data.server.models.comic.toDomainComic
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
        MarvelApiRest.service.getCharacters(ts, publicKey, hash, offset).toDomainCharacters

    override suspend fun getCharacterById(
        characterId: Int,
        ts: Long,
        publicKey: String,
        hash: String
    ): Characters =
        MarvelApiRest.service.getCharacterById(characterId, ts, publicKey, hash).toDomainCharacters

    override suspend fun getComics(
        characterId: Int,
        ts: Long,
        publicKey: String,
        hash: String
    ): Comic? =
        MarvelApiRest.service.getComics(characterId, ts, publicKey, hash)?.toDomainComic
}
