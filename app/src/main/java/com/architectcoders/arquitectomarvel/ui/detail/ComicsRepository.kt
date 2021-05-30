package com.architectcoders.arquitectomarvel.ui.detail

import androidx.lifecycle.asLiveData
import com.architectcoders.arquitectomarvel.data.database.MarvelDao
import com.architectcoders.arquitectomarvel.data.database.toComicEntityList
import com.architectcoders.domain.comics.Result.Companion.EMPTY_LIST_COMICS
import com.architectcoders.usecases.GiveMeDao
import com.architectcoders.usecases.GetRemoteComicsFromCharacterId
import com.architectcoders.usecases.InsertComicsForHeroLocal
import kotlinx.coroutines.flow.flow

class ComicsRepository(
    private val getRemoteComicsFromCharacterId: GetRemoteComicsFromCharacterId,
    private val insertComicsForHeroLocal: InsertComicsForHeroLocal,
    private val getComicsForHero: GiveMeDao,
) {

    fun getComics(idHero: Int) = networkBoundResource(
        query = {
                (getComicsForHero.invoke() as MarvelDao).selecetComicsForHero(idHero)
        },
        fetch = {
            val comic = getRemoteComicsFromCharacterId.invoke(idHero)
            val comicList = comic.comicData.results.toComicEntityList
            if (comicList.isEmpty()) EMPTY_LIST_COMICS.toComicEntityList
            else comicList
        },
        saveFetchResult = { comics ->
            val map = mapOf(
                "idHero" to idHero,
                "comics" to comics
            )
            insertComicsForHeroLocal.invoke(map)
        }
    ).asLiveData()
}
