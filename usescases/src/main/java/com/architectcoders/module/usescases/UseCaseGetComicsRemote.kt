package com.architectcoders.module.usescases

import com.architectcoders.module.domain.models.Comics.Comics

interface UseCaseGetComicsRemote {

    suspend fun invoke(characterId: Int): Comics
}