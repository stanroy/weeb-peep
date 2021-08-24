package com.stanroy.weebpeep.data.repository.dataSource

import com.stanroy.weebpeep.data.model.Anime

interface AnimeLocalDataSource {

    // DB
    suspend fun insertIntoDatabase(animeList: List<Anime>)
    suspend fun insertWithoutReplacing(animeList: List<Anime>)
    fun getAllAnime(): List<Anime>
    fun clearAll()

    // Favourite
    suspend fun addAnimeToFavourites(malId: Int)
    suspend fun deleteAnimeFromFavourite(malId: Int)
    suspend fun getFavouriteAnime(): List<Anime>
}
