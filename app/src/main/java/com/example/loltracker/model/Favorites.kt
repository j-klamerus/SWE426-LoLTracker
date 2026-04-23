package com.example.loltracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

// Creates favorites table through SQLite
@Entity(tableName = "favorites_table")
data class Favorites (
    @PrimaryKey(autoGenerate = true)
    var favId: Long = 0L,
    @ColumnInfo(name = "profile_name")
    var profileName: String = "",
    @ColumnInfo(name = "region")
    var region: String = ""
)