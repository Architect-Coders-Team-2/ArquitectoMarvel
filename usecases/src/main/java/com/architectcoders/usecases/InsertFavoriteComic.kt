package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.comics.Result

class InsertFavoriteComic(private val characterRepository: CharacterRepository):
IUseCase<Any,Result>{
    override suspend fun invoke(param: Result) =
        characterRepository.insertFavoriteComic(param)


}
