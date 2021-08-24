package com.stanroy.weebpeep.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stanroy.weebpeep.domain.usecase.AddAnimeToFavouritesUseCase
import com.stanroy.weebpeep.domain.usecase.ClearAnimeListUseCase
import com.stanroy.weebpeep.domain.usecase.FetchUpcomingAnimeUseCase

class UpcomingAnimeViewModelFactory(
    private val app: Application,
    private val fetchUpcomingAnimeUseCase: FetchUpcomingAnimeUseCase,
    private var clearAnimeListUseCase: ClearAnimeListUseCase,
    private var addAnimeToFavouritesUseCase: AddAnimeToFavouritesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingAnimeViewModel::class.java)) {
            return UpcomingAnimeViewModel(
                app,
                fetchUpcomingAnimeUseCase,
                clearAnimeListUseCase,
                addAnimeToFavouritesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
