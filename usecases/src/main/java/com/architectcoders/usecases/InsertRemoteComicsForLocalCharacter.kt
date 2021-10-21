package com.architectcoders.usecases

import com.architectcoders.data.repository.MarvelRepository
import javax.inject.Inject

class InsertRemoteComicsForLocalCharacter @Inject constructor(
    private val marvelRepository: MarvelRepository
) : InvokeUseCase<Map<String, Any>, Unit> {
    override suspend fun invoke(param: Map<String, Any>): Unit =
        marvelRepository.insertRemoteComicsForLocalCharacter(param)
}
