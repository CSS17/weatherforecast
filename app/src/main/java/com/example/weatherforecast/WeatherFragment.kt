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
        Log.d("LATLONG", "${data?.get(0)}  ${data?.get(1)}")

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
            binding = FragmentWeatherBinding.inflate(inflater, container, false)

            // Getting Data
            val temperature = Temperature()

            val city = temperature.getCity(latitude, longitude, requireContext())

            binding.textview.text = city

            viewModel.weatherData.observe(viewLifecycleOwner, Observer { weatherData ->
                if (weatherData != null) {
                    val currentTemperature = weatherData.current.temp
                    val currentIcon=weatherData.current.weather.get(0).description
                    Log.d("DAILY","${currentIcon}")
                    celcius=temperature.convertToCelcius(currentTemperature)
                    binding.degree.text="$celcius°"
                    setWeatherIcon(currentIcon)
                    Log.d("SATURN",currentTemperature.toString())
                }
            })

            return binding.root
        }


    fun setWeatherIcon(weather: String) {
        when (weather) {
            "clear sky" -> binding.imageView.setImageResource(R.drawable.clear_sky)

            "few clouds"->binding.imageView.setImageResource(R.drawable.few_clouds)

            "scattered clouds"->binding.imageView.setImageResource(R.drawable.scattered_clouds)

            "broken clouds","overcast clouds"->binding.imageView.setImageResource(R.drawable.broken_clouds)

            "mist","smoke","haze","sand/dust whirls","fog","sand","dust","volcanic ash","squalls","tornado"->binding.imageView.setImageResource(R.drawable.mist)

            "light snow","snow","heavy snow","sleet","light shower sleet","shower sleet","light rain and snow",
            "rain and snow","light shower snow","shower snow","heavy shower snow","freezing rain"->binding.imageView.setImageResource(R.drawable.snow)

            "light rain","moderate rain","heavy intensity rain","very heavy rain","extreme rain"->binding.imageView.setImageResource(R.drawable.rain)

            "light intensity shower rain","shower rain","heavy intensity shower rain","ragged shower rain"
                ,"shower drizzle","heavy shower rain and drizzle","shower rain and drizzle","heavy intensity drizzle rain",
            "drizzle rain","light intensity drizzle rain","heavy intensity drizzle","drizzle","light intensity drizzle"->binding.imageView.setImageResource(R.drawable.shower_rain)

            "thunderstorm with light rain","thunderstorm with rain","thunderstorm with heavy rain","light thunderstorm","thunderstorm",
            "heavy thunderstorm","ragged thunderstorm","thunderstorm with light drizzle","thunderstorm with drizzle","thunderstorm with heavy drizzle"->binding.imageView.setImageResource(R.drawable.thunderstorm)

            else -> "nothing found"
        }


    }

    }










