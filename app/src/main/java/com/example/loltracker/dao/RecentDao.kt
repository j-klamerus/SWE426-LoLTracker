package com.example.loltracker.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.loltracker.model.Recent

// SQLite commands for database
@Dao
interface RecentDao {

    @Insert
    suspend fun insert(recent: Recent)

    @Update
    suspend fun update(recent: Recent)

    @Delete
    suspend fun delete(recent: Recent)

    @Query("DELETE FROM recents_table")
    suspend fun deleteAll()

    // Queries that return LiveData must NOT be suspend
    @Query("SELECT * FROM recents_table WHERE recId = :recId")
    fun get(recId: Long): LiveData<Recent>

    @Query("SELECT * FROM recents_table ORDER BY recId DESC")
    fun getAll(): LiveData<List<Recent>>
}