package com.architectcoders.arquitectomarvel.data.database

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface MarvelDao {

    @Query("SELECT * FROM heroentity ORDER BY name")
    fun getPagingSourceFromCharacterEntity(): PagingSource<Int, HeroEntity>

    @Query("SELECT * FROM heroentity WHERE id = :characterId")
    suspend fun getLocalCharacterById(characterId: Int): HeroEntity

    @Query("SELECT insertDate FROM heroentity LIMIT 1")
    suspend fun getLastTimeStampFromCharacterEntity(): Long?

    @Query("SELECT COUNT(id) FROM heroentity")
    suspend fun getLocalCharactersCount(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocalCharacters(heroList: List<HeroEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalFavoriteCharacter(favoriteHeroEntity: FavoriteHeroEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalFavoriteComic(comicEntity: ComicEntity)

    @Delete
    suspend fun deleteLocalFavoriteCharacter(favoriteHeroEntity: FavoriteHeroEntity)

    @Delete
    suspend fun deleteLocalFavoriteComic(comicEntity: ComicEntity)

    @Query("SELECT id FROM favoriteheroentity WHERE id = :id")
    suspend fun isLocalCharacterFavorite(id: Int): Int?

    @Query("DELETE FROM heroentity")
    suspend fun deleteAllLocalCharacters()
}
