package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.architectcoders.arquitectomarvel.ui.detail.ComicsRepository.Companion.COMICS
import com.architectcoders.arquitectomarvel.ui.detail.ComicsRepository.Companion.ID_HERO
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
    suspend fun getLocalCharactersCount(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocalCharacters(characterList: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Delete
    suspend fun deleteLocalFavoriteCharacter(favoriteCharacterEntity: FavoriteCharacterEntity)

    @Query("SELECT id FROM favoritecharacterentity WHERE id = :id")
    suspend fun isLocalCharacterFavorite(id: Int): Int?

    @Query("DELETE FROM characterentity")
    suspend fun deleteAllLocalCharacters()

    // Comics
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComics(comicEntity: List<ComicEntity>)

    @Query("DELETE FROM COMICENTITY WHERE idHero = :idHero")
    suspend fun deleteComicsForHero(idHero: Int)

    @Query("SELECT * FROM ComicEntity WHERE idHero = :idHero")
    fun selecetComicsForHero(idHero: Int): Flow<List<ComicEntity>>

    @Transaction
    suspend fun fetchComicsForHero(map: Map<String, Any>) {
        val idHero = map[ID_HERO] as Int
        val comics = map[COMICS] as List<ComicEntity>
        deleteComicsForHero(idHero)
        comics.map {
            it.idHero = idHero
        }
        insertComics(comics)
    }

}
