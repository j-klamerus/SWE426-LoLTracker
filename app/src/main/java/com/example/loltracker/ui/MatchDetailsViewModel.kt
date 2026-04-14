package com.example.loltracker.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loltracker.BuildConfig
import com.example.loltracker.model.SummonerMatchData
import com.example.loltracker.network.RiotAccountApi
import kotlinx.coroutines.launch

class MatchDetailsViewModel : ViewModel() {

    val matchData = MutableLiveData<SummonerMatchData?>()
    val errorMessage = MutableLiveData<String?>()

    fun loadMatch(matchId: String) {
        viewModelScope.launch {
            try {
                val response = RiotAccountApi.api.getSummonerMatchData(
                    matchId,
                    BuildConfig.RIOT_API_KEY
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