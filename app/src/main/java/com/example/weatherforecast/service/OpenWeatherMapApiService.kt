package com.example.weatherforecast.service

import com.example.weatherforecast.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApiService {
    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("appid") appId: String
    ): Response<WeatherModel>

}