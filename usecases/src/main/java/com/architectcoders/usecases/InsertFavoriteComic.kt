package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.comics.Result

class InsertFavoriteComic(private val characterRepository: CharacterRepository) {
    suspend fun invoke(comic: Result) = characterRepository.insertFavoriteComic(comic)
}
