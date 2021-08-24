package com.stanroy.weebpeep.data.di

import com.stanroy.weebpeep.data.api.JikanApiService
import com.stanroy.weebpeep.data.repository.dataSource.AnimeRemoteDataSource
import com.stanroy.weebpeep.data.repository.dataSourceImpl.AnimeRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideAnimeRemoteDataSource(jikanApiService: JikanApiService): AnimeRemoteDataSource {
        return AnimeRemoteDataSourceImpl(jikanApiService)
    }
}