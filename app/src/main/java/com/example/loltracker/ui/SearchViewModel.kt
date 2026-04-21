package com.example.loltracker.ui
import android.util.Log
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

data class ProfileNavEvent(
    val accountText: String,
    val puuid: String,
    val profileIconId: Int,
    val summonerLevel: Long,
    val region: String
)

private var searchCount = 0

class SearchViewModel : ViewModel() {
    val accountData = MutableLiveData<AccountResponse>()
    val profileData = MutableLiveData<ProfileResponse>()
    val errorMessage = MutableLiveData<String>()
    val selectedRegion = MutableLiveData<String>("NA")

    private val _navToProfile = MutableSharedFlow<ProfileNavEvent>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val navToProfile = _navToProfile.asSharedFlow()

    fun searchPlayer(accountUser: String, accountTag: String, apiKey: String) {

        searchCount++
        Log.d("SearchVM", "searchPlayer called: $searchCount  user=$accountUser tag=$accountTag region=${selectedRegion.value}")

        val region = selectedRegion.value.toString()
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
                val accountResponse = accountApi.getAccountPUUID(accountUser, accountTag, apiKey)
                if (!accountResponse.isSuccessful) {
                    errorMessage.value = "Account Error: ${accountResponse.code()}"
                    return@launch
                }

                val account = accountResponse.body()
                if (account == null) {
                    errorMessage.value = "Account Error: empty response"
                    return@launch
                }
                accountData.value = account

                val profileResponse = summonerApi.getAccountProfile(account.puuid, apiKey)
                if (!profileResponse.isSuccessful) {
                    errorMessage.value = "Profile Error: ${profileResponse.code()}"
                    return@launch
                }

                val profile = profileResponse.body()
                if (profile == null) {
                    errorMessage.value = "Profile Error: empty response"
                    return@launch
                }
                profileData.value = profile

                // Emit navigation event ONCE, only when both are valid
                _navToProfile.emit(
                    ProfileNavEvent(
                        accountText = "${account.gameName}#${account.tagLine}",
                        puuid = account.puuid,
                        profileIconId = profile.profileIconId,
                        summonerLevel = profile.summonerLevel,
                        region = region
                    )
                )
            } catch (e: Exception) {
                errorMessage.value = "Exception: ${e.message}"
            }
        }
    }
}