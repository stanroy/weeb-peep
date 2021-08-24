package com.stanroy.weebpeep.data.repository

import com.stanroy.weebpeep.data.api.Resource
import com.stanroy.weebpeep.data.api.Status
import com.stanroy.weebpeep.data.model.Anime
import com.stanroy.weebpeep.data.repository.dataSource.AnimeLocalDataSource
import com.stanroy.weebpeep.data.repository.dataSource.AnimeRemoteDataSource
import com.stanroy.weebpeep.domain.repository.AnimeRepository
import timber.log.Timber

class AnimeRepositoryImpl(
    private val animeRemoteDataSource: AnimeRemoteDataSource,
    private val animeLocalDataSource: AnimeLocalDataSource
) :
    AnimeRepository {

    // Fetching anime, always checks for database data first, if its empty it tries to retrieve
    // data from JikanAPI. If that fails it throws an error.
    override suspend fun fetchUpcomingAnime(isUserOnline: Boolean): Resource<List<Anime>> {
        return fetchFromDatabase(isUserOnline)
    }

    private suspend fun fetchFromDatabase(isUserOnline: Boolean): Resource<List<Anime>> {

        var animeList: List<Anime>
        var resourceData: Resource<List<Anime>>

        try {
            animeList = animeLocalDataSource.getAllAnime().sortedByDescending { anime ->
                anime.malId
            }

            if (!isUserOnline) {
                resourceData = if (animeList.isNotEmpty()) {
                    Timber.d("FETCHED FROM DB")
                    Resource.Success(animeList, "Fetched data from database")
                } else {
                    Resource.Error(
                        "Cache empty, No internet connection",
                        type = Status.ERR_CACHE_INTERNET
                    )
                }
            } else {
                resourceData = if (animeList.isNotEmpty()) {
                    Timber.d("FETCHED FROM DB")
                    Resource.Success(animeList, "Fetched data from database")
                } else {
                    val fetchedAnime = fetchFromAPI()
                    if (fetchedAnime.isNotEmpty()) {
                        animeLocalDataSource.insertIntoDatabase(fetchedAnime)
                        animeList = animeLocalDataSource.getAllAnime()
                        Resource.Success(animeList, "Fetched data from API")
                    } else {
                        Resource.Error("Cannot connect to API servers", type = Status.ERR_API)
                    }
                }
            }

        } catch (e: Exception) {
            resourceData = Resource.Error(e.message)
        }

        return resourceData
    }

    private suspend fun fetchFromAPI(): List<Anime> {
        var animeList: List<Anime> = listOf()

        Timber.d("Getting Anime from API")
        if (animeRemoteDataSource.getUpcomingAnime().isSuccessful) {
            Timber.d("Returning list form API")
            animeRemoteDataSource.getUpcomingAnime().body()?.let { result ->
                animeList = result.anime.sortedByDescending { anime ->
                    anime.malId
                }

            }
        }

        return animeList
    }

    override fun clearAll() {
        animeLocalDataSource.clearAll()
    }

    override suspend fun addAnimeToFavourites(malId: Int) {
        animeLocalDataSource.addAnimeToFavourites(malId)
    }

    override suspend fun deleteAnimeFromFavourite(malId: Int) {
        animeLocalDataSource.deleteAnimeFromFavourite(malId)
    }

    override suspend fun getFavouriteAnime(): List<Anime> {
        return animeLocalDataSource.getFavouriteAnime()
    }
}
