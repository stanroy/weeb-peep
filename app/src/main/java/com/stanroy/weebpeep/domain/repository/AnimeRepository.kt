package com.stanroy.weebpeep.domain.repository

import com.stanroy.weebpeep.data.api.Resource
import com.stanroy.weebpeep.data.model.Anime

interface AnimeRepository {

    // Network
    suspend fun fetchUpcomingAnime(isUserOnline: Boolean): Resource<List<Anime>>

    // Local
    fun clearAll()

    suspend fun addAnimeToFavourites(malId: Int)
    suspend fun deleteAnimeFromFavourite(malId: Int)
    suspend fun getFavouriteAnime(): List<Anime>
}
