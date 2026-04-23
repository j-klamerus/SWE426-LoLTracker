package com.example.loltracker.network

private const val DDRAGON_VERSION = "16.7.1"
private const val DDRAGON_BASE = "https://ddragon.leagueoflegends.com/cdn/$DDRAGON_VERSION"

// DDRAGON (Data Dragon) is Riot's massive asset group. The intended use is to be stored locally (and not called like how we are), but this is fine.
// Searches for champion icon through specified page path
fun championIconUrl(championName: String): String {
    return "$DDRAGON_BASE/img/champion/$championName.png"
}
// Searches for item icon through specified page path
fun itemIconUrl(itemId: Int): String {
    return "$DDRAGON_BASE/img/item/$itemId.png"
}
// Takes spell ID from API data and returns the given spell icon
fun spellFileName(spellId: Int): String? {
    return when (spellId) {
        1 -> "SummonerBoost"
        3 -> "SummonerExhaust"
        4 -> "SummonerFlash"
        6 -> "SummonerHaste"
        7 -> "SummonerHeal"
        11 -> "SummonerSmite"
        12 -> "SummonerTeleport"
        13 -> "SummonerMana"
        14 -> "SummonerDot"
        21 -> "SummonerBarrier"
        32 -> "SummonerSnowball"
        else -> null
    }
}

// Uses the spellFileName above to get the actual image
fun spellIconUrl(spellId: Int): String? {
    val fileName = spellFileName(spellId) ?: return null
    return "$DDRAGON_BASE/img/spell/$fileName.png"
}

// Searches for user profile icon through specified page path
fun userIconUrl(iconNum: String): String {
    return "$DDRAGON_BASE/img/profileicon/$iconNum.png"
}