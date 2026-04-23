package com.example.loltracker.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.loltracker.model.Favorites

// Whenever changes to Favorites entity are made, version needs to be incremented
@Database(entities = [Favorites::class], version = 2, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract val favoritesDao: FavoritesDao

    companion object{
        @Volatile
        private var INSTANCE: FavoritesDatabase? = null

        // Migrates database from v1 to v2 (since we made a change)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE favorites_table ADD COLUMN region TEXT NOT NULL DEFAULT ''")
            }
        }

        // Creates database
        fun getInstance(context: Context): FavoritesDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoritesDatabase::class.java,
                        "favorites_database"
                    )
                        .addMigrations(MIGRATION_1_2)
                        .build()
                        .also { INSTANCE = it }
                }
                return instance
            }
        }
    }
}