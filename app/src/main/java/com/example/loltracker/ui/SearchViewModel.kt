package com.example.loltracker.ui
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loltracker.model.AccountResponse
import com.example.loltracker.model.ProfileResponse
import com.example.loltracker.network.RiotAccountApiAM
import com.example.loltracker.network.RiotAccountApiAS
import com.example.loltracker.network.RiotAccountApiEU
import com.example.loltracker.network.RiotSummonerApiAM
import com.example.loltracker.network.RiotSummonerApiAS
import com.example.loltracker.network.RiotSummonerApiEUNE
import com.example.loltracker.network.RiotSummonerApiEUW
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    val accountData = MutableLiveData<AccountResponse>()
    val profileData = MutableLiveData<ProfileResponse>()
    val errorMessage = MutableLiveData<String>()

    val selectedRegion = MutableLiveData<String>("NA")


    fun searchPlayer(accountUser: String, accountTag: String, apiKey: String) {

        val region = selectedRegion.value
        val accountApi = when (region) {
            "NA" -> RiotAccountApiAM.api
            "EUW", "EUNE" -> RiotAccountApiEU.api
            "KR" -> RiotAccountApiAS.api
            else -> RiotAccountApiAM.api
        }

        val summonerApi = when (region) {
            "NA" -> RiotSummonerApiAM.api
            "EUW" -> RiotSummonerApiEUW.api
            "EUNE" -> RiotSummonerApiEUNE.api
            "KR" -> RiotSummonerApiAS.api
            else -> RiotSummonerApiAM.api
        }

        viewModelScope.launch {
            try {
                val accountResponse = accountApi.getAccountPUUID(
                    accountUser,
                    accountTag,
                    apiKey
                )

                if (accountResponse.isSuccessful) {
                    val data = accountResponse.body()
                    accountData.value = data

                    data?.puuid?.let { puuid ->
                        val profileResponse = summonerApi.getAccountProfile(puuid, apiKey)
                        if (profileResponse.isSuccessful) {
                            profileData.value = profileResponse.body()
                        } else {
                            errorMessage.value = "Profile Error: ${profileResponse.code()}"
                        }
                    }

                } else { // added additional error response for account, not just profile - andy
                    errorMessage.value = "Profile Error: ${accountResponse.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Exception: ${e.message}"
            }
        }
    }
}