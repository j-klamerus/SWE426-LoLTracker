package com.example.loltracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.loltracker.model.Favorites

@Dao
interface FavoritesDao {

    @Insert
    suspend fun insert(favorites: Favorites)

    @Update
    suspend fun update(favorites: Favorites)

    @Delete
    suspend fun delete(favorites: Favorites)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAll()

    // Queries that return LiveData must NOT be suspend
    @Query("SELECT * FROM favorites_table WHERE favId = :favId")
    fun get(favId: Long): LiveData<Favorites>

    @Query("SELECT * FROM favorites_table ORDER BY favId DESC")
    fun getAll(): LiveData<List<Favorites>>
}