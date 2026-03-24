package com.example.loltracker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// For getting account's puuid
object RiotAccountApi {
    val api: RiotApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://americas.api.riotgames.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotApiService::class.java)
    }
}

// For getting account's level / profile icon
object RiotSummonerApi {
    val api: RiotApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://na1.api.riotgames.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotApiService::class.java)
    }
}