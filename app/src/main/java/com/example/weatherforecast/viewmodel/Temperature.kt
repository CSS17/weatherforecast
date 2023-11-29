package com.example.weatherforecast.viewmodel

import android.content.Context
import com.example.weatherforecast.service.LocationUtils
import java.text.SimpleDateFormat
import java.util.*

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

    fun convertDateTime(date: Long): String {
        val dateFormat = SimpleDateFormat("EEEE", Locale("en_US"))
        val formattedDate = dateFormat.format(Date(date * 1000)) // Unix zaman damgasını milisaniyeye çevir
        return formattedDate
    }



}