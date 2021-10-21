package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class GetPagingSourceFromCharacterEntity @Inject constructor(
    private val marvelRepository: MarvelRepository
) {
    fun invoke(): Any? = marvelRepository.getPagingSourceFromCharacterEntity()
}
