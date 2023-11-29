package com.example.weatherforecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecast.constants.Constants.APPID
import com.example.weatherforecast.constants.Constants.EXCLUDE
import com.example.weatherforecast.databinding.FragmentWeatherBinding
import com.example.weatherforecast.viewmodel.CityViewModel
import com.example.weatherforecast.viewmodel.Temperature

class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: CityViewModel
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    var celcius=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentWeatherBinding.inflate(layoutInflater)

        val data = arguments?.getDoubleArray("KEY_DATA")
        Log.d("LOL", "${data?.get(0)}  ${data?.get(1)}")

        if (data != null) {
            latitude = data[0]
            longitude = data[1]
        }

        viewModel = ViewModelProvider(this).get(CityViewModel::class.java)
        viewModel.getWeatherData(latitude, longitude, EXCLUDE, APPID)
    }
        companion object {
            fun newInstance(data: DoubleArray): WeatherFragment {
                val fragment = WeatherFragment()
                val args = Bundle()
                args.putDoubleArray("KEY_DATA", data)
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
            binding = FragmentWeatherBinding.inflate(inflater, container, false)

            // Getting Data
            val temperature = Temperature()

            val city = temperature.getCity(latitude, longitude, requireContext())

            binding.textview.text = city

            viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherData ->
                // weatherData'yi kullanarak gerekli güncellemeleri yapın
                if (weatherData != null) {
                    val currentTemperature = weatherData.current.temp
                    celcius=temperature.convertToCelcius(currentTemperature)
                    binding.degree.text="$celcius°"
                    Log.d("SATURN",currentTemperature.toString())
                }
            })

            return binding.root
        }









    }



