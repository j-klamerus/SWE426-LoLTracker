package com.example.loltracker.ui
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loltracker.model.AccountResponse
import com.example.loltracker.model.ProfileResponse
import com.example.loltracker.network.RiotAccountApi
import com.example.loltracker.network.RiotSummonerApi
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    val accountData = MutableLiveData<AccountResponse?>()
    val profileData = MutableLiveData<ProfileResponse>()
    val errorMessage = MutableLiveData<String>()

    fun searchPlayer(accountUser: String, accountTag: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val accountResponse = RiotAccountApi.api.getAccountPUUID(
                    accountUser,
                    accountTag,
                    apiKey
                )

                if (accountResponse.isSuccessful) {
                    val data = accountResponse.body()
                    accountData.value = data

                    data?.puuid?.let { puuid ->
                        val profileResponse = RiotSummonerApi.api.getAccountProfile(puuid, apiKey)
                        if (profileResponse.isSuccessful) {
                            profileData.value = profileResponse.body()
                        } else {
                            errorMessage.value = "Profile Error: ${profileResponse.code()}"
                        }
                    }

                }
            } catch (e: Exception) {
                errorMessage.value = "Exception: ${e.message}"
            }
        }
    }
}