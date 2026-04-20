package com.example.loltracker.network

import com.example.loltracker.model.AccountResponse
import com.example.loltracker.model.ProfileResponse
import com.example.loltracker.data.SummonerMatchData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Connects kotlin backend code to HTTP requests with Retrofit
interface RiotApiService {

    //@GET is API command with variables as @PATH. Data is sent through "Response" to the associated data class
    @GET("riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
    suspend fun getAccountPUUID(
        @Path("gameName") gameName: String,
        @Path("tagLine") tagLine: String,
        @Query("api_key") apiKey: String
    ): Response<AccountResponse>

    @GET("lol/summoner/v4/summoners/by-puuid/{puuid}")
    suspend fun getAccountProfile(
        @Path("puuid") puuid: String,
        @Query("api_key") apiKey: String
    ): Response<ProfileResponse>

    @GET("/lol/match/v5/matches/by-puuid/{puuid}/ids")
    suspend fun getAccountMatchIDS(
        @Path("puuid") puuid: String,
        @Query("api_key") apiKey: String
    ): Response<List<String>>

    @GET("/lol/match/v5/matches/{matchId}")
    suspend fun  getSummonerMatchData(
        @Path("matchId") matchId: String,
        @Query("api_key") apiKey: String
    ): Response<SummonerMatchData>
}