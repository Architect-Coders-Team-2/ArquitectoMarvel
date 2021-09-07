package com.architectcoders.arquitectomarvel.fakeDataSources

import com.architectcoders.arquitectomarvel.data.database.ComicEntity
import com.architectcoders.arquitectomarvel.data.database.FavoriteCharacterEntity
import com.architectcoders.arquitectomarvel.data.database.toComicComicList
import com.architectcoders.arquitectomarvel.data.database.toFavoriteCharacterEntity
import com.architectcoders.arquitectomarvel.ui.common.CHARACTER_ID
import com.architectcoders.arquitectomarvel.ui.common.COMICS
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.domain.character.Character
import com.architectcoders.domain.comic.Comic
import kotlinx.coroutines.flow.flowOf
import mockedCharacter
import javax.inject.Inject

class FakeLocalDataSource @Inject constructor() : LocalDataSource {

    var fakeCharacterList: MutableList<Character> = mutableListOf()
    var fakeFavoriteCharacterList: MutableList<FavoriteCharacterEntity> = mutableListOf()
    var fakeComicList: MutableList<Comic> = mutableListOf()
    var pwd: String = "pwd"
    var hint: String = "hint"

    override suspend fun getLocalCharacterById(characterId: Int): Character = mockedCharacter

    override suspend fun getLastTimeStampFromCharacterEntity(): Long = 1L

    override suspend fun getLocalCharactersCount(): Int = 1

    override fun isLocalCharacterFavorite(characterId: Int): Any = flowOf(1)

    override suspend fun insertAllLocalCharacters(characterList: List<Character>) {
        fakeCharacterList.addAll(characterList)
    }

    override suspend fun deleteAllLocalCharacters(): Unit = fakeCharacterList.clear()

    override suspend fun getLocalFavoriteCharacters(): Any = flowOf(fakeFavoriteCharacterList)

    override suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character) {
        fakeCharacterList.add(favoriteCharacter)
    }

    override suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character) {
        fakeFavoriteCharacterList.remove(favoriteCharacter.toFavoriteCharacterEntity)
    }

    override fun getComicsForCharacter(characterId: Int): Any = flowOf(fakeComicList)

    override suspend fun insertRemoteComicsForLocalCharacter(map: Map<String, Any>) {
        val idCharacter = map[CHARACTER_ID] as Int
        val comics = map[COMICS] as List<ComicEntity>
        fakeComicList.clear()
        comics.map {
            it.characterId = idCharacter
        }
        fakeComicList.addAll(comics.toComicComicList)
    }

    override suspend fun isPasswordAlreadyStored(): Boolean = true

    override suspend fun saveCredentials(password: String, recoveryHint: String) {
        pwd = password
        hint = recoveryHint
    }

    override suspend fun deleteCredentials() {
        pwd = ""
        hint = ""
    }

    override suspend fun isPasswordCorrect(password: String): Boolean = password == pwd

    override suspend fun isRecoveryHintCorrect(recoveryHint: String): Boolean = recoveryHint == hint

    override suspend fun deleteAllLocalFavoriteCharacter() = fakeFavoriteCharacterList.clear()

    override fun getPagingSourceFromCharacterEntity(): Any = Unit
}
