package com.example.loltracker.model

// stuff specifically for individual matches
data class PlayerRowData(
    val summonerName: String,
    val championName: String,
    val role: String,
    val kills: Int,
    val deaths: Int,
    val assists: Int
)