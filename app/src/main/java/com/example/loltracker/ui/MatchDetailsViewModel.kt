package com.example.loltracker.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loltracker.BuildConfig
import com.example.loltracker.data.SummonerMatchData
import com.example.loltracker.network.RiotAccountApiAM
import com.example.loltracker.network.RiotAccountApiEU
import com.example.loltracker.network.RiotAccountApiAS
import kotlinx.coroutines.launch

class MatchDetailsViewModel : ViewModel() {

    val matchData = MutableLiveData<SummonerMatchData?>()
    val errorMessage = MutableLiveData<String?>()

    fun loadMatch(matchId: String, region: String) {

        // Selects proper region to make API call to based on spinner input
        val api = when (region) {
            "NA" -> RiotAccountApiAM.api
            "EUW", "EUNE" -> RiotAccountApiEU.api
            "KR" -> RiotAccountApiAS.api
            else -> RiotAccountApiAM.api
        }

        // Requests match data and assigns to Summoner live data when successful
        viewModelScope.launch {
            try {
                val response = api.getSummonerMatchData(
                    matchId,
                    BuildConfig.apiKey
                )

                if (response.isSuccessful) {
                    matchData.value = response.body()
                } else {
                    errorMessage.value = "Match Details Error: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Exception: ${e.message}"
            }
        }
    }
}