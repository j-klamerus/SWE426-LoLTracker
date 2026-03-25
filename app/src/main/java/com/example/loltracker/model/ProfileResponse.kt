package com.example.loltracker.model

data class ProfileResponse(
    val puuid: String,
    val profileIconId: String,
    val revisionDate: Long,
    val summonerLevel: Long,
)