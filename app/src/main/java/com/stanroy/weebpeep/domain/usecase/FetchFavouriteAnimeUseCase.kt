package com.stanroy.weebpeep.domain.usecase

import com.stanroy.weebpeep.data.model.Anime
import com.stanroy.weebpeep.domain.repository.AnimeRepository

class FetchFavouriteAnimeUseCase(private val animeRepository: AnimeRepository) {
    suspend fun execute(): List<Anime> {
        return animeRepository.getFavouriteAnime()
    }
}