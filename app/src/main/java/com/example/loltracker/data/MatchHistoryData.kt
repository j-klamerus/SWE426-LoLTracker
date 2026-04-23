package com.example.loltracker.data

// Data class used in MatchDataMapper
data class PlayerMatchSummary(
    val matchId: String,
    val gameCreation: Long,
    val gameDuration: Long,

    val championName: String,
    var win: Boolean,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
)