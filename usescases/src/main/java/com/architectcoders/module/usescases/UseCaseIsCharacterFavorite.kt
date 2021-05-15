package com.architectcoders.module.usescases

interface UseCaseIsCharacterFavorite{

    suspend fun invoke(characterId: Int): Boolean
}