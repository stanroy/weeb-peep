package com.stanroy.weebpeep.data.api

import com.stanroy.weebpeep.BuildConfig
import com.stanroy.weebpeep.data.model.APIResponse
import com.stanroy.weebpeep.data.model.Anime
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



interface JikanApiService {

    // Remote data source
    @GET("season/later")
    suspend fun getUpcomingAnime(): Response<APIResponse>

    // Local data source
    suspend fun saveAnime(anime: Anime)
    suspend fun deleteAnime(anime: Anime)
    fun getSavedAnime()
}

