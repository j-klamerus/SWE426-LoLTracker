package com.example.loltracker.model

import com.google.gson.annotations.SerializedName

data class SummonerMatchData(@SerializedName("metadata") val metadata: MatchMetadata,
                             @SerializedName("info") val info: MatchInfo
)

data class MatchMetadata(
    val matchId: String,
    val participants: List<String>
)

data class MatchInfo(
    val gameMode: String,
    val gameDuration: Long,
    val gameType: String,
    val participants: List<Participant>
)

data class Participant(
    val puuid: String,
    val summonerName: String,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val win: Boolean,
    val championName: String
)