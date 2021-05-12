package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.comics.Result

class DeleteFavoriteComic(private val characterRepository: CharacterRepository):IUseCase<Any,Result> {
    override suspend fun invoke(comic: Result) = characterRepository.deleteFavoriteComic(comic)
}
