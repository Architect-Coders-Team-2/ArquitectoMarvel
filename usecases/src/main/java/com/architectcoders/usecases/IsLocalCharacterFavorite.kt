package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class IsLocalCharacterFavorite @Inject constructor(
    private val marvelRepository: MarvelRepository
) {
    fun invoke(param: Int): Any =
        marvelRepository.isLocalCharacterFavorite(param)
}
