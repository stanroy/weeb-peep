package com.stanroy.weebpeep.domain.usecase

import com.stanroy.weebpeep.domain.repository.AnimeRepository

class AddAnimeToFavouritesUseCase(private val animeRepository: AnimeRepository) {
    suspend fun execute(malId: Int) {
        animeRepository.addAnimeToFavourites(malId)
    }
}
