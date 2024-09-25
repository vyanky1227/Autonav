package com.example.autonav

import retrofit2.http.GET
import retrofit2.http.Query

interface EventbriteApi {
    @GET("events/search/")
    suspend fun getEvents(
        @Query("q") query: String,
        @Query("location") location: String, // Correctly formatted for the API
        @Query("token") apiKey: String = "LXSBNJZ4AG452ZW5DN4R"
    ): EventbriteResponse


}
