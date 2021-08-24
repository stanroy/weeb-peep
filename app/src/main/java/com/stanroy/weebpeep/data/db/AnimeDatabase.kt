package com.stanroy.weebpeep.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stanroy.weebpeep.data.model.Anime
import com.stanroy.weebpeep.data.model.Converters

@Database(entities = [Anime::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AnimeDatabase() : RoomDatabase() {

    abstract fun getAnimeDAO(): AnimeDAO

    companion object {

        @Volatile
        private var INSTANCE: AnimeDatabase? = null

        fun getInstance(context: Context): AnimeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AnimeDatabase::class.java,
                        "anime_list_databae"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
