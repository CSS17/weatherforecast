package com.example.weatherforecast.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.weatherforecast.R
import com.example.weatherforecast.sharedpreferences.UserPermission
import com.example.weatherforecast.databinding.ActivityWeatherForecastBinding

class WeatherForecastActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherForecastBinding
    private var coordinateList= doubleArrayOf(0.0,0.0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWeatherForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (UserPermission.getPermission(this@WeatherForecastActivity) =="true"){
            val intent = intent
            coordinateList[0] = intent.getDoubleExtra("LATITUDE",0.0)
            coordinateList[1] = intent.getDoubleExtra("LONGITUDE",0.0)
            Log.d("COORDINATES","LATIDUDE:${coordinateList[0]} ---- LONGITUDE:${coordinateList[1]}")
            val fragment = WeatherFragment.newInstance(coordinateList,"activity")
            replaceFragment(fragment)
        }
        else{
            replaceFragment(PermissionDeniedFragment())
        }

        binding.searchBar.setOnClickListener {
            val intent = Intent(this@WeatherForecastActivity, SearchScreenActivity::class.java)
            startActivity(intent)
        }



    }


    private fun replaceFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }




}