package com.example.loltracker.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.loltracker.model.Recent

// Whenever changes to Recents entity are made, version needs to be incremented
@Database(entities = [Recent::class], version = 1, exportSchema = false)
abstract class RecentDatabase : RoomDatabase() {
    abstract val recentDao: RecentDao

    companion object{
        @Volatile
        private var INSTANCE: RecentDatabase? = null

        fun getInstance(context: Context): RecentDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecentDatabase::class.java,
                        "recent_database"
                    )
                        .build()
                        .also { INSTANCE = it }
                }
                return instance
            }
        }
    }
}