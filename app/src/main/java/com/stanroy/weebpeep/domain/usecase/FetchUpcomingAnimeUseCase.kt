package com.stanroy.weebpeep.domain.usecase

import com.stanroy.weebpeep.data.api.Resource
import com.stanroy.weebpeep.data.model.Anime
import com.stanroy.weebpeep.domain.repository.AnimeRepository

class FetchUpcomingAnimeUseCase(private val animeRepository: AnimeRepository) {
    suspend fun execute(isUserOnline: Boolean): Resource<List<Anime>> {
        return animeRepository.fetchUpcomingAnime(isUserOnline)
    }
}
