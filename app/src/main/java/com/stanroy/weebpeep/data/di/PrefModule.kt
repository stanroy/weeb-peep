package com.stanroy.weebpeep.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.stanroy.weebpeep.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PrefModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesForTimeout(app: Application): SharedPreferences {
        return app.applicationContext.getSharedPreferences(
            app.getString(R.string.preference_weeb_peep),
            Context.MODE_PRIVATE
        )
    }
}