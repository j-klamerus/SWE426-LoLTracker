package com.example.loltracker.model

// Data class that is used to store API data when getting PUUID
data class AccountResponse(
    val puuid: String,
    val gameName: String,
    val tagLine: String
)