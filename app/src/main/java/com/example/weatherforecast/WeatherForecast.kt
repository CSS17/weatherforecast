package com.example.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.weatherforecast.databinding.ActivityWeatherForecastBinding

class WeatherForecast : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherForecastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWeatherForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val permission = intent.getStringExtra("PERMISSION").toString()

        if (permission=="Evet"){
            replaceFragment(WeatherFragment())
        }
        else{
            replaceFragment(PermissionDeniedFragment())
        }


    }


    private fun replaceFragment(fragment:Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()

    }
}