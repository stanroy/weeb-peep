package com.stanroy.weebpeep.domain.usecase

import com.stanroy.weebpeep.domain.repository.AnimeRepository

class DeleteAnimeFromFavouritesUseCase(private val animeRepository: AnimeRepository) {
    suspend fun execute(malId: Int) {
        animeRepository.deleteAnimeFromFavourite(malId)
    }
}