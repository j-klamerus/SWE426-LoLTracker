package com.example.loltracker.data

fun SummonerMatchData.toPlayerSummary(targetPuuid: String): PlayerMatchSummary? {
    val idx = metadata.participants.indexOf(targetPuuid)
    if (idx < 0) return null

    val p = info.participants[idx]
    //val cs = p.totalMinionsKilled + p.neutralMinionsKilled

    return PlayerMatchSummary(
        matchId = metadata.matchId,
        //queueId = info.queueId,
        gameCreation = info.gameCreation,
        gameDuration = info.gameDuration,

        championName = p.championName,
        win = p.win,
        kills = p.kills,
        deaths = p.deaths,
        assists = p.assists,
        //cs = cs,
        //gold = p.goldEarned,

        /*
        itemIds = listOf(p.item0, p.item1, p.item2, p.item3, p.item4, p.item5, p.item6),
        summoner1Id = p.summoner1Id,
        summoner2Id = p.summoner2Id
        */
    )
}