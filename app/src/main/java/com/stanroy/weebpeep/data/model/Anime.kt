package com.stanroy.weebpeep.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "anime_list")
data class Anime(
    @SerializedName("continuing")
    val continuing: Boolean,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("kids")
    val kids: Boolean,
    @PrimaryKey
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("members")
    val members: Int,
    @SerializedName("producers")
    val producers: List<Producer>,
    @SerializedName("r18")
    val r18: Boolean,
    @SerializedName("source")
    val source: String,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @ColumnInfo(name = "is_saved")
    val isSaved: Boolean = false
)
