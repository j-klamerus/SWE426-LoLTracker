package com.example.loltracker.model

// Data specifically for individual matches
data class PlayerRowData(
    val summonerName: String,
    val championName: String,
    val role: String,
    val kills: Int,
    val deaths: Int,
    val assists: Int
)