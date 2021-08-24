package com.stanroy.weebpeep.data.repository.dataSource

import com.stanroy.weebpeep.data.model.APIResponse
import retrofit2.Response

interface AnimeRemoteDataSource {

    suspend fun getUpcomingAnime(): Response<APIResponse>
}
