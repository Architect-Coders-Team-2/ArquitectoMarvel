package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import com.architectcoders.domain.character.Character
import javax.inject.Inject

class InsertLocalFavoriteCharacter @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<Character, Unit> {
    override suspend fun invoke(param: Character) =
        marvelRepository.insertLocalFavoriteCharacter(param)
}
