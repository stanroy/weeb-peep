package com.stanroy.weebpeep.data.model

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromGenre(genres: List<Genre>): String {
        return genres.joinToString(",")
    }

    @TypeConverter
    fun toGenre(genres: String): List<Genre> {
        return genres.split(",").map { Genre(it) }.toList()
    }

    @TypeConverter
    fun fromProducer(producers: List<Producer>): String {
        return producers.joinToString(",")
    }

    @TypeConverter
    fun toProducer(producers: String): List<Producer> {
        return producers.split(",").map { Producer(it) }.toList()
    }
}
