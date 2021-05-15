package com.architectcoders.usecases

import com.architectcoders.data.repository.CharacterRepository
import com.architectcoders.domain.characters.Characters

interface IUseCase <T,K>{
 suspend fun invoke(param: T): K?
}