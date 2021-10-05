package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import com.architectcoders.arquitectomarvel.ui.common.md5
import com.architectcoders.arquitectomarvel.ui.common.wrapEspressoIdlingResource
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.domain.character.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val marvelDao: MarvelDao) : LocalDataSource {

    override suspend fun getLocalCharacterById(characterId: Int): Character =
        wrapEspressoIdlingResource {
            marvelDao.getLocalCharacterById(characterId).toDomainCharacter
        }

    override suspend fun getLastTimeStampFromCharacterEntity(): Long? =
        wrapEspressoIdlingResource {
            marvelDao.getLastTimeStampFromCharacterEntity()
        }

    override suspend fun getLocalCharactersCount(): Int =
        wrapEspressoIdlingResource {
            marvelDao.getLocalCharactersCount()
        }

    override suspend fun insertAllLocalCharacters(characterList: List<Character>) =
        wrapEspressoIdlingResource {
            marvelDao.insertAllLocalCharacters(characterList.toCharacterEntityList)
        }

    override suspend fun deleteAllLocalCharacters() =
        wrapEspressoIdlingResource {
            marvelDao.deleteAllLocalCharacters()
        }

    override suspend fun getLocalFavoriteCharacters(): Flow<List<FavoriteCharacterEntity>> =
        wrapEspressoIdlingResource {
            marvelDao.getLocalFavoriteCharacters()
        }

    override suspend fun insertLocalFavoriteCharacter(favoriteCharacter: Character) =
        wrapEspressoIdlingResource {
            marvelDao.insertLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)
        }

    override suspend fun deleteLocalFavoriteCharacter(favoriteCharacter: Character) =
        wrapEspressoIdlingResource {
            marvelDao.deleteLocalFavoriteCharacter(favoriteCharacter.toFavoriteCharacterEntity)
        }

    override fun isLocalCharacterFavorite(characterId: Int): Flow<Int> =
        wrapEspressoIdlingResource {
            marvelDao.isLocalCharacterFavorite(characterId)
        }

    override fun getPagingSourceFromCharacterEntity(): PagingSource<Int, CharacterEntity> =
        wrapEspressoIdlingResource {
            marvelDao.getPagingSourceFromCharacterEntity()
        }

    override suspend fun insertRemoteComicsForLocalCharacter(map: Map<String, Any>) =
        wrapEspressoIdlingResource {
            marvelDao.insertRemoteComicsForLocalCharacter(map)
        }

    override fun getComicsForCharacter(characterId: Int): Any =
        wrapEspressoIdlingResource {
            marvelDao.selectComicsForCharacter(characterId)
        }

    override suspend fun isPasswordAlreadyStored(): Boolean =
        wrapEspressoIdlingResource {
            marvelDao.areCredentialsAlreadyStored() > 0
        }

    override suspend fun saveCredentials(password: String, recoveryHint: String) =
        wrapEspressoIdlingResource {
            marvelDao.saveCredentials(
                CredentialsEntity(
                    password = password.md5,
                    recoveryHint = recoveryHint.md5
                )
            )
        }

    override suspend fun deleteCredentials() =
        wrapEspressoIdlingResource {
            marvelDao.deleteCredentials()
        }

    override suspend fun isPasswordCorrect(password: String): Boolean =
        wrapEspressoIdlingResource {
            val credentials = marvelDao.getCredentials()
            return credentials?.password == password.md5
        }

    override suspend fun isRecoveryHintCorrect(recoveryHint: String): Boolean =
        wrapEspressoIdlingResource {
            val credentials = marvelDao.getCredentials()
            return credentials?.recoveryHint == recoveryHint.md5
        }

    override suspend fun deleteAllLocalFavoriteCharacter() =
        wrapEspressoIdlingResource {
            marvelDao.deleteAllLocalFavoriteCharacter()
        }
}
