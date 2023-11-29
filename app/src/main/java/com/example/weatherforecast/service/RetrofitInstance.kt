package com.example.weatherforecast.service

import com.example.weatherforecast.constants.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val openWeatherMapApiService: OpenWeatherMapApiService by lazy {
        retrofit.create(OpenWeatherMapApiService::class.java)
    }
}
