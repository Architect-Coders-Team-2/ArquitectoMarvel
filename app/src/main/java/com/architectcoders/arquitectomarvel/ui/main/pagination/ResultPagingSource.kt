package com.architectcoders.arquitectomarvel.ui.main.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.architectcoders.arquitectomarvel.BuildConfig
import com.architectcoders.arquitectomarvel.ui.common.INITIAL
import com.architectcoders.arquitectomarvel.ui.common.md5
import com.architectcoders.domain.characters.Result
import com.architectcoders.usecases.GetCharacters
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class ResultPagingSource(
    private val getCharacters: GetCharacters
) : PagingSource<Int, Result>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Result> {
        return try {
            val ts = System.currentTimeMillis()
            val offset = params.key ?: 0
            val response = getCharacters.invoke(
                offset, ts,
                "$ts${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_API_KEY}".md5
            )
            val results = response.characterData?.results!!
            Timber.d("qq_Repository.getCharactersRemote: ----- ()")
            Timber.d("qq_Repository.getCharactersRemote: $offset (offset)")
            Timber.d(
                "qq_Repository.getCharactersRemote: ${response.characterData?.results?.size} (response.characterData?.results?.size)"
            )
            LoadResult.Page(
                data = results,
                prevKey = null, // Only paging forward.
                nextKey = if (results.isEmpty()) null else offset + INITIAL
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(INITIAL) ?: anchorPage?.nextKey?.minus(INITIAL)
        }
    }
}
