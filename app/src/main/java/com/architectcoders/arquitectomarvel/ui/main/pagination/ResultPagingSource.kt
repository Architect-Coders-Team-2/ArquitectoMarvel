package com.architectcoders.arquitectomarvel.ui.main.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.architectcoders.arquitectomarvel.ui.common.REQUEST_LIMIT
import com.architectcoders.domain.characters.Result
import com.architectcoders.usecases.GetRemoteCharacters
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class ResultPagingSource(
    private val getRemoteCharacters: GetRemoteCharacters
) : PagingSource<Int, Result>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Result> {
        return try {
            val offset = params.key ?: 0
            val response = getRemoteCharacters.invoke(offset)
            val results = response.characterData?.results!!
            Timber.d("qq_Repository.getCharactersRemote: ----- ()")
            Timber.d("qq_Repository.getCharactersRemote: $offset (offset)")
            Timber.d(
                "qq_Repository.getCharactersRemote: ${response.characterData?.results?.size} (response.characterData?.results?.size)"
            )
            LoadResult.Page(
                data = results,
                prevKey = null, // Only paging forward.
                nextKey = if (results.isEmpty()) null else offset + REQUEST_LIMIT
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
            anchorPage?.prevKey?.plus(REQUEST_LIMIT) ?: anchorPage?.nextKey?.minus(REQUEST_LIMIT)
        }
    }
}
