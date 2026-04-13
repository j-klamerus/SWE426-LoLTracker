package com.example.loltracker.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loltracker.ui.SearchViewModel
import com.example.loltracker.model.AccountResponse
import com.example.loltracker.model.ProfileResponse
import com.example.loltracker.model.MatchIDResponse
import com.example.loltracker.network.RiotAccountApi
import com.example.loltracker.network.RiotSummonerApi
import androidx.lifecycle.MutableLiveData
import com.example.loltracker.model.MatchID
import com.example.loltracker.model.SummonerMatchData
import kotlinx.coroutines.launch
import com.example.loltracker.BuildConfig

class ProfileStatViewModel : ViewModel() {
    val matchDataResults = MutableLiveData<List<SummonerMatchData>>()

    fun searchMatchIDS(puuid: String) {
        viewModelScope.launch {
            try {
                val matchIDList = RiotAccountApi.api.getAccountMatchIDS(
                    puuid,
                    BuildConfig.apiKey
                ).body()
                val results = mutableListOf<SummonerMatchData>()

                matchIDList?.forEach { matchID ->
                    val matchData = RiotAccountApi.api.getSummonerMatchData(
                        matchID,
                        BuildConfig.apiKey
                    )
                    if (matchData.isSuccessful) {
                        matchData.body()?.let { results.add(it) }
                    }
                }
                //update live data
                matchDataResults.postValue(results)
            } catch (e: Exception) {

            }
        }
    }
}