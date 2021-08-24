package com.stanroy.weebpeep.presentation.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stanroy.weebpeep.data.model.Anime
import timber.log.Timber

class AnimePagingSource(val data: List<Anime>) : PagingSource<Int, Anime>() {
    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        return try {

            return LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = params.key
            )

        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }
}