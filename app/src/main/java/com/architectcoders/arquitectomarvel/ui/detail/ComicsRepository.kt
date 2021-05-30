package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.asLiveData
import com.architectcoders.arquitectomarvel.data.database.ComicEntity
import com.architectcoders.arquitectomarvel.data.database.toComicEntityList
import com.architectcoders.domain.comics.Result.Companion.EMPTY_LIST_COMICS
import com.architectcoders.usecases.GetComicsForHero
import com.architectcoders.usecases.GetRemoteComicsFromCharacterId
import com.architectcoders.usecases.InsertComicsForHeroLocal
import kotlinx.coroutines.flow.Flow

class ComicsRepository(
    private val getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId,
    private val insertComicsForHeroLocal: InsertComicsForHeroLocal,
    private val getComicsForHero: GetComicsForHero,
) {

    fun getComics(idHero: Int) = networkBoundResource(
        query = {
                getComicsForHero.invoke(idHero) as Flow<List<ComicEntity>>
        },
        fetch = {
            val comic = getRemoteComicsFromCharacterId.invoke(idHero)
            val comicList = comic.comicData.results.toComicEntityList
            if (comicList.isEmpty()) EMPTY_LIST_COMICS.toComicEntityList
            else comicList
        },
        saveFetchResult = { comics ->
            val map = mapOf(
                ID_HERO to idHero,
                COMICS to comics
            )
            insertComicsForHeroLocal.invoke(map)
        }
    ).asLiveData()

    companion object {
        const val ID_HERO = "idHero"
        const val COMICS = "comics"
    }
}
