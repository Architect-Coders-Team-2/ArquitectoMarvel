package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class IsLocalCharacterFavorite(private val marvelRepository: MarvelRepository) {
    fun invoke(param: Int): Any =
        marvelRepository.isLocalCharacterFavorite(param)
}
