package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.asLiveData
import com.architectcoders.arquitectomarvel.data.database.ComicEntity
import com.architectcoders.arquitectomarvel.data.database.toComicEntityList
import com.architectcoders.arquitectomarvel.ui.common.CHARACTER_ID
import com.architectcoders.arquitectomarvel.ui.common.COMICS
import com.architectcoders.usecases.GetComicsForCharacter
import com.architectcoders.usecases.GetRemoteComicsFromCharacterId
import com.architectcoders.usecases.InsertComicsForCharacterLocal
import kotlinx.coroutines.flow.Flow

class GetComicsInteractor(
    private val getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId,
    private val insertComicsForCharacterLocal: InsertComicsForCharacterLocal,
    private val getComicsForCharacter: GetComicsForCharacter,
) {
    fun networkBoundResourceResult(characterId: Int) = networkBoundResource(
        query = {
            getComicsForCharacter.invoke(characterId) as Flow<List<ComicEntity>>
        },
        fetch = {
            val comicsPayload = getRemoteComicsFromCharacterId.invoke(characterId)
            comicsPayload?.comicData?.comics?.toComicEntityList ?: emptyList()
        },
        saveFetchResult = { comics ->
            val map = mapOf(
                CHARACTER_ID to characterId,
                COMICS to comics
            )
            insertComicsForCharacterLocal.invoke(map)
        }
    ).asLiveData()
}
