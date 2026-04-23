package com.example.loltracker.data

// Takes a players's puuid and collects their match data
fun SummonerMatchData.toPlayerSummary(targetPuuid: String): PlayerMatchSummary? {
    val idx = metadata.participants.indexOf(targetPuuid)
    if (idx < 0) return null

    val p = info.participants[idx]

    // Returns match data of participant
    return PlayerMatchSummary(
        matchId = metadata.matchId,
        gameCreation = info.gameCreation,
        gameDuration = info.gameDuration,

        championName = p.championName,
        win = p.win,
        kills = p.kills,
        deaths = p.deaths,
        assists = p.assists,
    )
}