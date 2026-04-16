package com.example.loltracker.data

data class PlayerMatchSummary(
    val matchId: String,
    //val queueId: Int,
    val gameCreation: Long,
    val gameDuration: Long,

    val championName: String,
    var win: Boolean,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    //val cs: Int,
    //val gold: Int,

    //val itemIds: List<Int>,
    //val summoner1Id: Int,
    //val summoner2Id: Int
)