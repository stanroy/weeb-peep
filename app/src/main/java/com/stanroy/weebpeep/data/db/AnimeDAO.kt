package com.stanroy.weebpeep.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stanroy.weebpeep.data.model.Anime

@Dao
interface AnimeDAO {

    // Database Anime
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntoDatabase(animeList: List<Anime>)

    //    @Query("SELECT * from anime_list")
//    suspend fun getItems(): List<Anime>
//
    suspend fun insertWithoutReplacing(newAnimeList: List<Anime>) {

//        val animeInDataBase = getItems()
//        val listToSave = mutableListOf<Anime>()
//
//        if (animeInDataBase.isEmpty()) {
//            listToSave.addAll(newAnimeList)
//        } else {
//            listToSave.addAll(animeInDataBase)
//
//            newAnimeList.forEach { newAnime ->
//                animeInDataBase.forEach { oldAnime ->
//                    if (newAnime.malId != oldAnime.malId) {
//                        listToSave.add(newAnime)
//                    }
//                }
//            }
//        }
//        insertIntoDatabase(listToSave)
    }

    @Query("SELECT * FROM anime_list WHERE is_saved = 0 ORDER BY malId DESC")
    fun getAllAnime(): List<Anime>

    @Query("DELETE FROM anime_list WHERE is_saved = 0")
    fun clearAll()

    // Favourite anime
    @Query("UPDATE anime_list SET is_saved = 1 WHERE malId = :malId")
    suspend fun addAnimeToFavourites(malId: Int)

    @Query("UPDATE anime_list SET is_saved = 0 WHERE malId = :malId")
    suspend fun deleteAnimeFromFavourites(malId: Int)

    @Query("SELECT * FROM anime_list WHERE is_saved = 1 ORDER BY malId DESC")
    fun getFavouriteAnime(): List<Anime>
}
