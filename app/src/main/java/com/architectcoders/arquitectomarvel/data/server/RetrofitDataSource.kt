package com.architectcoders.arquitectomarvel.data.server

import com.architectcoders.arquitectomarvel.data.server.uiEntities.character.toLocalCharactersPayload
import com.architectcoders.arquitectomarvel.data.server.uiEntities.comics.toLocalComicsPayload
import com.architectcoders.data.source.CredentialsDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.character.CharactersPayload
import com.architectcoders.domain.comic.ComicsPayload
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(
    override val credentialsDataSource: CredentialsDataSource,
    private val apiService: ApiService
) : RemoteDataSource {


    override suspend fun getRemoteCharacters(offset: Int): CharactersPayload =
        apiService.getCharacters(
            credentialsDataSource.timeStamp,
            credentialsDataSource.publicKey,
            credentialsDataSource.hash,
            offset
        ).toLocalCharactersPayload

    override suspend fun getRemoteCharacterById(characterId: Int): CharactersPayload =
        apiService.getCharacterById(
            characterId,
            credentialsDataSource.timeStamp,
            credentialsDataSource.publicKey,
            credentialsDataSource.hash
        ).toLocalCharactersPayload

    override suspend fun getRemoteComics(characterId: Int): ComicsPayload? =
        apiService.getComics(
            characterId,
            credentialsDataSource.timeStamp,
            credentialsDataSource.publicKey,
            credentialsDataSource.hash
        )?.toLocalComicsPayload
}
