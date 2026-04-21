package com.example.loltracker.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loltracker.network.RiotAccountApiAM
import com.example.loltracker.network.RiotAccountApiEU
import com.example.loltracker.network.RiotAccountApiAS
import androidx.lifecycle.MutableLiveData
import com.example.loltracker.BuildConfig
import com.example.loltracker.data.PlayerMatchSummary
import com.example.loltracker.data.SummonerMatchData
import com.example.loltracker.data.toPlayerSummary
import kotlinx.coroutines.launch

class ProfileStatViewModel : ViewModel() {
    //val matchDataResults = MutableLiveData<List<SummonerMatchData>>() (Historic)
    val matchDataHistory = MutableLiveData<List<PlayerMatchSummary>>()

    fun searchMatchIDS(puuid: String, region: String) {

        val api = when (region) {
            "NA" -> RiotAccountApiAM.api
            "EUW", "EUNE" -> RiotAccountApiEU.api
            "KR" -> RiotAccountApiAS.api
            else -> RiotAccountApiAM.api
        }

        viewModelScope.launch {
            try {
                // Calls getAccountMatchIDS function based on the player's puuid and project API key.
                // Returns 5 match IDs
                val matchIDList = api.getAccountMatchIDS(
                    puuid,
                    BuildConfig.apiKey
                ).body()

                val results = mutableListOf<PlayerMatchSummary>()

                // Takes each match ID & API key to submit a request for the actual match data.
                matchIDList?.forEach { matchID ->
                    val matchData = api.getSummonerMatchData(
                        matchID,
                        BuildConfig.apiKey
                    )
                    if (matchData.isSuccessful) {
                        val summary = matchData.body()?.toPlayerSummary(puuid)
                        if (summary != null) { results.add(summary) }

                        //matchData.body()?.let { results.add(it) } (What output the raw data to the history UI)
                    }

                    matchDataHistory.postValue(results)
                }
                //update live data
                //matchDataResults.postValue(results) (Historic)
            } catch (e: Exception) {
                // or maybe here?
            }
        }
    }
}