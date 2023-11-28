package com.example.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.weatherforecast.databinding.ActivityWeatherForecastBinding

class WeatherForecast : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWeatherForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var latitude=0.0

        if (UserPermission.getPermission(this@WeatherForecast)=="true"){
            val intent = intent
            latitude = intent.getDoubleExtra("LATITUDE",0.0)
            val longitude = intent.getDoubleExtra("LONGITUDE",0.0)
            Log.d("COORDINATES","LATIDUDE:$latitude ---- LONGITUDE:$longitude")
            val fragment = WeatherFragment.newInstance(latitude)
            replaceFragment(WeatherFragment())
        }
        else{
            replaceFragment(PermissionDeniedFragment())
        }


    }


    private fun replaceFragment(fragment:Fragment){
        val fragmentManager=supportFragmentManager
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()

    }




}