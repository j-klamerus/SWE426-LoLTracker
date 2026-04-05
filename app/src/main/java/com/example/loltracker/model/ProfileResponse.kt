package com.example.loltracker.model

// Data class to store API data for when PUUID is used to get profile data.
data class ProfileResponse(
    val profileIconId: Int,
    val revisionDate: Long,
    val puuid: String,
    val summonerLevel: Long
)