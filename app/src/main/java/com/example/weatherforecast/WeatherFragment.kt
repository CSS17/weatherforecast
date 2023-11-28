package com.example.weatherforecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherforecast.databinding.ActivityWeatherForecastBinding
import com.example.weatherforecast.databinding.FragmentWeatherBinding

class WeatherFragment:Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentWeatherBinding.inflate(layoutInflater)
    }
    companion object {
        fun newInstance(data: Double): WeatherFragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            args.putDouble("KEY_DATA", data)
            Log.d("LOL",args.toString())
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Fragment'ın oluşturulması
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        // Veriyi almak
        val data = arguments?.getString("KEY_DATA", "")
        Log.d("LOL",data.toString())
        binding.textview.text=data
        // TODO: Veriyi kullan

        return view
    }


}