package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.architectcoders.arquitectomarvel.ui.common.CHARACTER_ID
import com.architectcoders.arquitectomarvel.ui.common.COMICS
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao {

    @Query("SELECT * FROM characterentity ORDER BY name")
    fun getPagingSourceFromCharacterEntity(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characterentity WHERE id = :characterId")
    suspend fun getLocalCharacterById(characterId: Int): CharacterEntity

    @Query("SELECT insertDate FROM characterentity LIMIT 1")
    suspend fun getLastTimeStampFromCharacterEntity(): Long?

    @Query("SELECT COUNT(id) FROM characterentity")
    suspend fun getLocalCharactersCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocalCharacters(characterList: List<CharacterEntity>)

    @Query("SELECT * FROM favoritecharacterentity ORDER BY name")
    fun getLocalFavoriteCharacters(): Flow<List<FavoriteCharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Delete
    suspend fun deleteLocalFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Delete
    suspend fun deleteLocalFavoriteComic(comicEntity: ComicEntity)

    @Query("SELECT COUNT(id) FROM favoritecharacterentity WHERE id = :id")
    fun isLocalCharacterFavorite(id: Int): Flow<Int>

    @Query("DELETE FROM characterentity")
    suspend fun deleteAllLocalCharacters()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComics(comicEntity: List<ComicEntity>)

    @Query("DELETE FROM COMICENTITY WHERE characterId = :idCharacter")
    suspend fun deleteComicsForCharacter(idCharacter: Int)

    @Query("SELECT * FROM ComicEntity WHERE characterId = :idCharacter")
    fun selectComicsForCharacter(idCharacter: Int): Flow<List<ComicEntity>>

    @Transaction
    suspend fun fetchComicsForCharacter(map: Map<String, Any>) {
        val idCharacter = map[CHARACTER_ID] as Int
        val comics = map[COMICS] as List<ComicEntity>
        deleteComicsForCharacter(idCharacter)
        comics.map {
            it.characterId = idCharacter
        }
        insertComics(comics)
    }

    @Query("SELECT COUNT(id) FROM credentialsentity")
    suspend fun areCredentialsAlreadyStored(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCredentials(credentialsEntity: CredentialsEntity)

    @Query("DELETE FROM credentialsentity")
    suspend fun deleteCredentials()

    @Query("SELECT * FROM credentialsentity")
    suspend fun getCredentials(): CredentialsEntity

    @Query("DELETE FROM favoritecharacterentity")
    suspend fun deleteAllLocalFavoriteCharacter()
}
