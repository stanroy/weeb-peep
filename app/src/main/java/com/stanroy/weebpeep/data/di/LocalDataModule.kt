package com.stanroy.weebpeep.data.di

import com.stanroy.weebpeep.data.db.AnimeDAO
import com.stanroy.weebpeep.data.repository.dataSource.AnimeLocalDataSource
import com.stanroy.weebpeep.data.repository.dataSourceImpl.AnimeLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideAnimeLocalDataSource(animeDAO: AnimeDAO): AnimeLocalDataSource {
        return AnimeLocalDataSourceImpl(animeDAO)
    }
}