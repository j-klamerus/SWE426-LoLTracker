package com.example.loltracker.data

import com.google.gson.annotations.SerializedName

data class SummonerMatchData(@SerializedName("metadata") val metadata: MatchMetadata,
                             @SerializedName("info") val info: MatchInfo
)
// All data needed for match details
data class MatchMetadata(
    val matchId: String,
    val participants: List<String>
)

data class MatchInfo(
    val gameMode: String,
    val gameDuration: Long,
    val gameCreation: Long,
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
    val championName: String,
    val teamId: Int,
    val teamPosition: String,
    val item0: Int,
    val item1: Int,
    val item2: Int,
    val item3: Int,
    val item4: Int,
    val item5: Int,
    val item6: Int,
    val totalMinionsKilled: Int,
    val goldEarned: Int,
    val totalDamageDealtToChampions: Int,
    val visionScore: Int,
    val summoner1Id: Int,
    val summoner2Id: Int
)