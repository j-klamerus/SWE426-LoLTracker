package com.example.loltracker.model

data class ProfileResponse(
    val profileIconId: Int,
    val revisionDate: Long,
    val puuid: String,
    val summonerLevel: Long
)