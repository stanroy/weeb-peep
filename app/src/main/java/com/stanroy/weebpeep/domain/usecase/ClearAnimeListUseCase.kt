package com.stanroy.weebpeep.domain.usecase

import com.stanroy.weebpeep.domain.repository.AnimeRepository

class ClearAnimeListUseCase(private val animeRepository: AnimeRepository) {
    fun execute() {
        animeRepository.clearAll()
    }
}
