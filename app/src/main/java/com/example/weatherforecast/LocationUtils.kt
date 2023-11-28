package com.example.weatherforecast

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.Locale

class LocationUtils(private val context: Context) {

    fun getAddressFromLocation(latitude: Double, longitude: Double): String? {

        val geocoder = Geocoder(context, Locale.ENGLISH)

        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1) as List<Address>

            if (addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                val cityName = address.locality ?:address.adminArea?: address.subAdminArea ?: address.adminArea
                val stateName: String = address.adminArea
                val countryName: String = address.countryName

                return "$cityName"
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}