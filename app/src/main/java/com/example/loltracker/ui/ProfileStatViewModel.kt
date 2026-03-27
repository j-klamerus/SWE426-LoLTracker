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

class ProfileStatViewModel : ViewModel() {
    val matchDataResults = MutableLiveData<List<SummonerMatchData>>()

    fun searchMatchIDS(puuid: String) {
        viewModelScope.launch {
            try {
                val matchIDList = RiotAccountApi.api.getAccountMatchIDS(
                    puuid,
                    "RGAPI-ee62c093-1c27-4eaa-96e3-c3fc416aa4e6"
                ).body()
                val results = mutableListOf<SummonerMatchData>()

                matchIDList?.forEach { matchID ->
                    val matchData = RiotAccountApi.api.getSummonerMatchData(
                        matchID,
                        "RGAPI-ee62c093-1c27-4eaa-96e3-c3fc416aa4e6"
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