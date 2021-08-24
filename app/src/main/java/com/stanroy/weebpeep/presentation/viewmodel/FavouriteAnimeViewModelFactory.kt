package com.stanroy.weebpeep.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stanroy.weebpeep.domain.usecase.DeleteAnimeFromFavouritesUseCase
import com.stanroy.weebpeep.domain.usecase.FetchFavouriteAnimeUseCase

class FavouriteAnimeViewModelFactory(
    private val app: Application,
    private val fetchFavouriteAnimeUseCase: FetchFavouriteAnimeUseCase,
    private val deleteAnimeFromFavouritesUseCase: DeleteAnimeFromFavouritesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteAnimeViewModel::class.java)) {
            return FavouriteAnimeViewModel(
                app,
                fetchFavouriteAnimeUseCase,
                deleteAnimeFromFavouritesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}