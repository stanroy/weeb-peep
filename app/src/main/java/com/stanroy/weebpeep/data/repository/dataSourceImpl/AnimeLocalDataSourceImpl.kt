package com.stanroy.weebpeep.data.repository.dataSourceImpl

import com.stanroy.weebpeep.data.db.AnimeDAO
import com.stanroy.weebpeep.data.model.Anime
import com.stanroy.weebpeep.data.repository.dataSource.AnimeLocalDataSource

class AnimeLocalDataSourceImpl(private val animeDAO: AnimeDAO) : AnimeLocalDataSource {
    override suspend fun insertIntoDatabase(animeList: List<Anime>) {
        animeDAO.insertIntoDatabase(animeList)
    }

    override suspend fun insertWithoutReplacing(animeList: List<Anime>) {
        animeDAO.insertWithoutReplacing(animeList)
    }

    override fun getAllAnime(): List<Anime> {
        return animeDAO.getAllAnime()
    }

    override fun clearAll() {
        animeDAO.clearAll()
    }

    override suspend fun addAnimeToFavourites(malId: Int) {
        animeDAO.addAnimeToFavourites(malId)
    }

    override suspend fun deleteAnimeFromFavourite(malId: Int) {
        animeDAO.deleteAnimeFromFavourites(malId)
    }

    override suspend fun getFavouriteAnime(): List<Anime> {
        return animeDAO.getFavouriteAnime()
    }
}
