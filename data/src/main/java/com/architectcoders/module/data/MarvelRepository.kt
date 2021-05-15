package com.architectcoders.module.data

import com.architectcoders.module.domain.models.Characters.Characters
import com.architectcoders.module.domain.models.Comics.Comics
import com.architectcoders.module.domain.models.Characters.ResultCharacters as CharacterResult

interface MarvelRepository {

    suspend fun getCharactersRemote(
        offset: Int
    ): Characters

    suspend fun getComicsFromCharacterRemote(
        characterId: Int
    ): Comics

    suspend fun insertFavoriteCharacter(favouriteCharacter: CharacterResult)

    suspend fun deleteFavoriteCharacters(favouriteCharacter: CharacterResult)

    suspend fun isCharacterFavorite(characterId: Int): Boolean

}