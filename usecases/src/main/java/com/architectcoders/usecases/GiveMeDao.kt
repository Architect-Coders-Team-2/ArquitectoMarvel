package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GiveMeDao(private val marvelRepository: MarvelRepository) {
    fun invoke(): Any =
        marvelRepository.giveMeDao()
}
