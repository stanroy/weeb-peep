package com.stanroy.weebpeep.data.model

import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("anime")
    val anime: List<Anime>,
    @SerializedName("season_name")
    val seasonName: String,
    @SerializedName("season_year")
    val seasonYear: Any
)
