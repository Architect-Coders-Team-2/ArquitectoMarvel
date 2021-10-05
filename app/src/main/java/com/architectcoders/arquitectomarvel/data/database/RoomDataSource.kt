package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import com.architectcoders.arquitectomarvel.ui.common.md5
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.domain.character.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val marvelDao: MarvelDao) : LocalDataSource {

    override suspend fun getLocalCharacterById(characterId: Int): Character =
        marvelDao.getLocalCharacterById(characterId).toDomainCharacter

    override suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        marvelDao.getLastTimeStampFromCharacterEntity()

    override suspend fun getLocalCharactersCount(): Int =
        marvelDao.getLocalCharactersCount()

    override suspend fun insertAllLocalCharacters(characterList: List<Character>) =
        marvelDao.insertAllLocalCharacters(characterList.toCharacterEntityList)

    override suspend fun deleteAllLocalCharacters() =
        marvelDao.deleteAllLocalCharacters()

    override suspend fun getLocalFavoriteCharacters(): Flow<List<FavoriteCharacterEntity>> =
        marvelDao.getLocalFavoriteCharacters()

    override suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character) =
        marvelDao.insertLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character) =
        marvelDao.deleteLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)

    override fun isLocalCharacterFavorite(characterId: Int): Flow<Int> =
        marvelDao.isLocalCharacterFavorite(characterId)

    override fun getPagingSourceFromCharacterEntity(): PagingSource<Int, CharacterEntity> =
        marvelDao.getPagingSourceFromCharacterEntity()

    override suspend fun insertRemoteComicsForLocalCharacter(map: Map<String, Any>) =
        marvelDao.insertRemoteComicsForLocalCharacter(map)

    override fun getComicsForCharacter(characterId: Int): Any =
        marvelDao.selectComicsForCharacter(characterId)

    override suspend fun isPasswordAlreadyStored(): Boolean =
        marvelDao.areCredentialsAlreadyStored() > 0

    override suspend fun saveCredentials(password: String, recoveryHint: String) {
        marvelDao.saveCredentials(
            CredentialsEntity(
                password = password.md5,
                recoveryHint = recoveryHint.md5
            )
        )
    }

    override suspend fun deleteCredentials() = marvelDao.deleteCredentials()

    override suspend fun isPasswordCorrect(password: String): Boolean {
        val credentials = marvelDao.getCredentials()
        return credentials?.password == password.md5
    }

    override suspend fun isRecoveryHintCorrect(recoveryHint: String): Boolean {
        val credentials = marvelDao.getCredentials()
        return credentials?.recoveryHint == recoveryHint.md5
    }

    override suspend fun deleteAllLocalFavoriteCharacter() =
        marvelDao.deleteAllLocalFavoriteCharacter()
}
