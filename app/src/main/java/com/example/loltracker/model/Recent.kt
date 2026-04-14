package com.example.loltracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "recents_table")
data class Recent (
    @PrimaryKey(autoGenerate = true)
    var recId: Long = 0L,
    @ColumnInfo(name = "profile_name")
    var profileName: String = "",
    @ColumnInfo(name = "region")
    var region: String = ""
)