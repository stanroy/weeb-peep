package com.stanroy.weebpeep.data.repository.dataSourceImpl

import com.stanroy.weebpeep.data.api.JikanApiService
import com.stanroy.weebpeep.data.model.APIResponse
import com.stanroy.weebpeep.data.repository.dataSource.AnimeRemoteDataSource
import retrofit2.Response

class AnimeRemoteDataSourceImpl(private val jikanApiService: JikanApiService) :
    AnimeRemoteDataSource {
    override suspend fun getUpcomingAnime(): Response<APIResponse> {
        return jikanApiService.getUpcomingAnime()
    }
}
