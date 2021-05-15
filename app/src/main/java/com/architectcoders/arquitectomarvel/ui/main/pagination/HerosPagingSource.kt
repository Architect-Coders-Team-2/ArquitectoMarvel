package com.architectcoders.arquitectomarvel.ui.main.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.architectcoders.arquitectomarvel.data.ui_models.ResultUI
import com.architectcoders.arquitectomarvel.data.ui_models.toResultUIList
import com.architectcoders.arquitectomarvel.data.usescases_impl.UseCaseGetCharactersRemoteImpl
import com.architectcoders.arquitectomarvel.ui.common.INCREMENT
import retrofit2.HttpException
import java.io.IOException

class HerosPagingSource(
    private val useCaseGetCharactersRemoteImpl: UseCaseGetCharactersRemoteImpl
) : PagingSource<Int, ResultUI>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ResultUI> {
        return try {
            val offset = params.key ?: 0
            val response = useCaseGetCharactersRemoteImpl.invoke(offset)
            val results = response.charactersDataCharacters?.resultCharacters!!.toResultUIList()
            LoadResult.Page(
                data = results,
                prevKey = null, // Only paging forward.
                nextKey = if (results.isEmpty()) null else offset + INCREMENT
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultUI>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(INCREMENT) ?: anchorPage?.nextKey?.minus(INCREMENT)
        }
    }
}