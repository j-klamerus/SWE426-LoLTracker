package com.example.loltracker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// For getting account's puuid, also use for fetching matches
object RiotAccountApiAM {
    val api: RiotApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://americas.api.riotgames.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotApiService::class.java)
    }
}

// For getting account's level / profile icon
object RiotSummonerApiAM {
    val api: RiotApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://na1.api.riotgames.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotApiService::class.java)
    }
}
// The two above are for the NA region. Each is duplicated for the other three
// Account for both EU and EUNE
    object RiotAccountApiEU {
        val api: RiotApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://europe.api.riotgames.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RiotApiService::class.java)
        }
    }


// EUW Summoner
    object RiotSummonerApiEUW {
        val api: RiotApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://euw1.api.riotgames.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RiotApiService::class.java)
        }
    }
// EUNE Summoner
object RiotSummonerApiEUNE {
    val api: RiotApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://eun1.api.riotgames.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RiotApiService::class.java)
    }
}
// KR Region
    object RiotAccountApiAS {
        val api: RiotApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://asia.api.riotgames.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RiotApiService::class.java)
        }
    }


    // For getting account's level / profile icon
    object RiotSummonerApiAS {
        val api: RiotApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://kr.api.riotgames.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RiotApiService::class.java)
        }
    }