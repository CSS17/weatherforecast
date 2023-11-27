package com.example.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherforecast.databinding.ActivityWeatherForecastBinding

class WeatherForecast : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherForecastBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWeatherForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val permission = intent.getStringExtra("PERMISSION").toString()
        binding.textview.text=permission
    }
}