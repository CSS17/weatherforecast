package com.example.weatherforecast.viewmodel

import android.content.Context
import com.example.weatherforecast.LocationUtils

class Temperature {

    fun getCity(latitude: Double, longitude: Double, context: Context): String {
        val locationUtils = LocationUtils(context)

        val address = latitude.let { lat ->
            longitude.let { lon ->
                locationUtils.getAddressFromLocation(lat, lon)
            }
        }

        return address ?: "Location Couldn't Found."
    }

    fun convertToCelcius(kelvin:Double):Int{
        val celcius=(kelvin-273.15).toInt()
        return celcius
    }





}