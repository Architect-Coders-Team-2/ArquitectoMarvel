package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository

class GetPagingSource(private val marvelRepository: MarvelRepository) {
    fun invoke(): Any? = marvelRepository.getPagingSource()
}
