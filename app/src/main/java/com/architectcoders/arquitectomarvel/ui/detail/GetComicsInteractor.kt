package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.asLiveData
import com.architectcoders.arquitectomarvel.data.database.ComicEntity
import com.architectcoders.arquitectomarvel.data.database.toComicEntityList
import com.architectcoders.usecases.GetComicsForHero
import com.architectcoders.usecases.GetRemoteComicsFromCharacterId
import com.architectcoders.usecases.InsertComicsForCharacterLocal
import kotlinx.coroutines.flow.Flow

class GetComicsInteractor(
    private val getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId,
    private val insertComicsForCharacterLocal: InsertComicsForCharacterLocal,
    private val getComicsForHero: GetComicsForHero,
) {
    fun invoke(idHero: Int) = networkBoundResource(
        query = {
            getComicsForHero.invoke(idHero) as Flow<List<ComicEntity>>
        },
        fetch = {
            val comicsPayload = getRemoteComicsFromCharacterId.invoke(idHero)
            comicsPayload?.comicData?.comics?.toComicEntityList ?: emptyList()
        },
        saveFetchResult = { comics ->
            val map = mapOf(
                ID_HERO to idHero,
                COMICS to comics
            )
            insertComicsForCharacterLocal.invoke(map)
        }
    ).asLiveData()

    companion object {
        const val ID_HERO = "idHero"
        const val COMICS = "comics"
    }
}