package com.stanroy.weebpeep.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.stanroy.weebpeep.data.model.Anime
import com.stanroy.weebpeep.domain.usecase.DeleteAnimeFromFavouritesUseCase
import com.stanroy.weebpeep.domain.usecase.FetchFavouriteAnimeUseCase
import com.stanroy.weebpeep.presentation.util.AnimePagingSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class FavouriteAnimeViewModel(
    app: Application,
    private val fetchFavouriteAnimeUseCase: FetchFavouriteAnimeUseCase,
    private val deleteAnimeFromFavouritesUseCase: DeleteAnimeFromFavouritesUseCase
) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val mainScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _animePagedList = MutableLiveData<Flow<PagingData<Anime>>>()
    val animePagedList: LiveData<Flow<PagingData<Anime>>>
        get() = _animePagedList


    private fun fetchAnime() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                val data = fetchFavouriteAnimeUseCase.execute()
                Timber.d("Fetching favs from db")
                _animePagedList.postValue(
                    Pager(
                        PagingConfig(pageSize = 10, maxSize = 200),
                        pagingSourceFactory = {
                            AnimePagingSource(data)
                        }).flow.cachedIn(viewModelScope)
                )
                AnimePagingSource(data).invalidate()
            }
        }
    }

    private fun deleteAnime(malId: Int) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                deleteAnimeFromFavouritesUseCase.execute(malId)
                fetchAnime()
            }
        }
    }

    fun fetchFavouriteFromDB() {
        fetchAnime()
    }

    fun removeFromFavourites(malId: Int) {
        deleteAnime(malId)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}