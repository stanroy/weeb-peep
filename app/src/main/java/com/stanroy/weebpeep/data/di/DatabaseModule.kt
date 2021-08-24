package com.stanroy.weebpeep.data.di

import android.app.Application
import com.stanroy.weebpeep.data.db.AnimeDAO
import com.stanroy.weebpeep.data.db.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Singleton
    @Provides
    fun provideAnimeDatabase(app: Application): AnimeDatabase {
        return AnimeDatabase.getInstance(app.applicationContext)
    }

    @Singleton
    @Provides
    fun providesAnimeDao(animeDatabase: AnimeDatabase): AnimeDAO {
        return animeDatabase.getAnimeDAO()
    }
}