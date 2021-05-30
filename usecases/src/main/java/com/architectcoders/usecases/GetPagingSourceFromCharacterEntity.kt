package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetPagingSourceFromCharacterEntity(private val marvelRepository: MarvelRepository) {
    fun invoke(): Any? = marvelRepository.getPagingSourceFromCharacterEntity()
}
