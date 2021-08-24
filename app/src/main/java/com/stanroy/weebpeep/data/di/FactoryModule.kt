package com.stanroy.weebpeep.data.di

import android.app.Application
import com.stanroy.weebpeep.domain.usecase.*
import com.stanroy.weebpeep.presentation.viewmodel.FavouriteAnimeViewModelFactory
import com.stanroy.weebpeep.presentation.viewmodel.UpcomingAnimeViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideUpcomingAnimeViewModelFactory(
        app: Application,
        fetchUpcomingAnimeUseCase: FetchUpcomingAnimeUseCase,
        clearAnimeListUseCase: ClearAnimeListUseCase,
        addAnimeToFavouritesUseCase: AddAnimeToFavouritesUseCase
    ): UpcomingAnimeViewModelFactory {
        return UpcomingAnimeViewModelFactory(
            app,
            fetchUpcomingAnimeUseCase,
            clearAnimeListUseCase,
            addAnimeToFavouritesUseCase
        )
    }

    @Singleton
    @Provides
    fun provideFavouriteAnimeViewModelFactory(
        app: Application,
        fetchFavouriteAnimeUseCase: FetchFavouriteAnimeUseCase,
        deleteAnimeFromFavouritesUseCase: DeleteAnimeFromFavouritesUseCase
    ): FavouriteAnimeViewModelFactory {
        return FavouriteAnimeViewModelFactory(
            app,
            fetchFavouriteAnimeUseCase,
            deleteAnimeFromFavouritesUseCase
        )
    }
}