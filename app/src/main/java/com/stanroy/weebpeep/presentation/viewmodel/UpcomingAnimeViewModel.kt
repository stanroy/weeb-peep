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
import com.stanroy.weebpeep.R
import com.stanroy.weebpeep.data.api.Resource
import com.stanroy.weebpeep.data.api.Status
import com.stanroy.weebpeep.data.model.Anime
import com.stanroy.weebpeep.data.util.CheckNetwork
import com.stanroy.weebpeep.domain.usecase.AddAnimeToFavouritesUseCase
import com.stanroy.weebpeep.domain.usecase.ClearAnimeListUseCase
import com.stanroy.weebpeep.domain.usecase.FetchUpcomingAnimeUseCase
import com.stanroy.weebpeep.presentation.util.AnimePagingSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class UpcomingAnimeViewModel(
    private val app: Application,
    private var fetchUpcomingAnimeUseCase: FetchUpcomingAnimeUseCase,
    private var clearAnimeListUseCase: ClearAnimeListUseCase,
    private var addAnimeToFavouritesUseCase: AddAnimeToFavouritesUseCase
) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val mainScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<Pair<Status, String?>>()
    val status: LiveData<Pair<Status, String?>>
        get() = _status

    private val _animePagedList = MutableLiveData<Flow<PagingData<Anime>>>()
    val animePagedList: LiveData<Flow<PagingData<Anime>>>
        get() = _animePagedList


    private var animeDataSize: Int = 0
    private var isUserOnline: Boolean = false


    init {
        isUserOnline = CheckNetwork.isNetworkAvailable(app.applicationContext)
    }

    private fun getUpcomingAnime() {
        mainScope.launch {
            _status.postValue(Pair(Status.LOADING, "Loading.."))
            withContext(Dispatchers.IO) {
                fetchAnime()
            }
        }
    }


    private suspend fun fetchAnime() {
        when (val list = fetchUpcomingAnimeUseCase.execute(isUserOnline)) {
            is Resource.Success -> {
                if (list.data.size > animeDataSize) {
                    Timber.d("List size: ${list.data.size}, Anime data size: $animeDataSize")
                    sendAnimeToPage(list.data)
                }
                _status.postValue(Pair(Status.SUCCESS, list.message))
            }


            is Resource.Error -> {
                when (list.type) {
                    Status.ERR_API -> {
                        _status.postValue(Pair(Status.ERR_API, list.message))
                    }

                    Status.ERR_CACHE_INTERNET -> {
                        _status.postValue(Pair(Status.ERR_CACHE_INTERNET, list.message))
                    }
                    else -> _status.postValue(
                        Pair(
                            Status.ERR_UNKNOWN,
                            app.applicationContext.getString(R.string.ERR_UNKNOWN_MSG)
                        )
                    )
                }
            }
        }
    }

    private fun sendAnimeToPage(data: List<Anime>) {
        _animePagedList.postValue(
            Pager(
                PagingConfig(pageSize = 1, maxSize = 200),
                pagingSourceFactory = {
                    AnimePagingSource(data)
                }).flow.cachedIn(viewModelScope)
        )

        animeDataSize = data.size
        AnimePagingSource(data).invalidate()
    }

    private fun saveAnime(malId: Int) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                addAnimeToFavouritesUseCase.execute(malId)
                animeDataSize = 0
                fetchAnime()
            }
        }
    }

    fun checkForDataChange() {
        getUpcomingAnime()
    }


    fun cleanRefreshAnimeList() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                animeDataSize = 0
                clearAnimeListUseCase.execute()
                getUpcomingAnime()
            }
        }
    }

    fun addToFavourites(malId: Int) {
        saveAnime(malId)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Timber.d("OnCleared")
    }
}
