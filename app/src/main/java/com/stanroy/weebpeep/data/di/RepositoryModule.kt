package com.stanroy.weebpeep.data.di

import com.stanroy.weebpeep.data.repository.AnimeRepositoryImpl
import com.stanroy.weebpeep.data.repository.dataSource.AnimeLocalDataSource
import com.stanroy.weebpeep.data.repository.dataSource.AnimeRemoteDataSource
import com.stanroy.weebpeep.domain.repository.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAnimeRepository(
        animeRemoteDataSource: AnimeRemoteDataSource,
        animeLocalDataSource: AnimeLocalDataSource
    ): AnimeRepository {
        return AnimeRepositoryImpl(animeRemoteDataSource, animeLocalDataSource)
    }
}