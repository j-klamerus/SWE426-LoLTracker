package com.example.loltracker.model

data class MatchIDResponse (
    val matches: List<MatchID>
)
data class MatchID(
    val gameId: String
)
