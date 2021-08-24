package com.stanroy.weebpeep.data.di

import com.stanroy.weebpeep.domain.repository.AnimeRepository
import com.stanroy.weebpeep.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {


    @Singleton
    @Provides
    fun provideFetchUpcomingAnimeUseCase(animeRepository: AnimeRepository): FetchUpcomingAnimeUseCase {
        return FetchUpcomingAnimeUseCase(animeRepository)
    }

    @Singleton
    @Provides
    fun provideClearAnimeListUseCase(animeRepository: AnimeRepository): ClearAnimeListUseCase {
        return ClearAnimeListUseCase(animeRepository)
    }

    @Singleton
    @Provides
    fun provideAddAnimeToFavouritesUseCase(animeRepository: AnimeRepository): AddAnimeToFavouritesUseCase {
        return AddAnimeToFavouritesUseCase(animeRepository)
    }

    @Singleton
    @Provides
    fun provideFetchFavouriteAnimeUseCase(animeRepository: AnimeRepository): FetchFavouriteAnimeUseCase {
        return FetchFavouriteAnimeUseCase(animeRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteAnimeFromFavouritesUseCase(animeRepository: AnimeRepository): DeleteAnimeFromFavouritesUseCase {
        return DeleteAnimeFromFavouritesUseCase(animeRepository)
    }
}