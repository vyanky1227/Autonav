package com.example.autonav

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EventbriteService {
    private const val BASE_URL = "https://www.eventbrite.com/api/v3/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: EventbriteApi = retrofit.create(EventbriteApi::class.java)
}
