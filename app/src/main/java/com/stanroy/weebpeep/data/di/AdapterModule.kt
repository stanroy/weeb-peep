package com.stanroy.weebpeep.data.di

import com.stanroy.weebpeep.presentation.util.AnimePagingAdapter
import com.stanroy.weebpeep.presentation.view.UpcomingAnimeFragment
import com.stanroy.weebpeep.presentation.viewmodel.UpcomingAnimeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {



    @Singleton
    @Provides
    fun providePagingAdapter(): AnimePagingAdapter {
        return AnimePagingAdapter()
    }
}