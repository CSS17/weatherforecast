package com.example.weatherforecast.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.constants.Constants.APPID
import com.example.weatherforecast.constants.Constants.EXCLUDE
import com.example.weatherforecast.databinding.FragmentWeatherBinding
import com.example.weatherforecast.viewmodel.CityViewModel
import com.example.weatherforecast.viewmodel.Temperature
import com.example.weatherforecast.viewmodel.WeatherAdapter

class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: CityViewModel
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    var celcius=0
    private var oneWeekTemperatureDay = mutableListOf<String>()
    private var oneWeekTemperatureNight = mutableListOf<String>()
    private var oneWeekWeather = mutableListOf<String>()
    private var dayOrder = mutableListOf<String>()

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

                    Log.d("SATURN",currentTemperature.toString())

                    weatherData.daily
                        .map { dailyData ->
                            val celciusday = temperature.convertToCelcius(dailyData.temp.day)
                            val celciusnight = temperature.convertToCelcius(dailyData.temp.night)
                            Log.d("NEPTUN", "${dailyData.dt}")

                            val days = temperature.convertDateTime(dailyData.dt.toLong())
                            Log.d("NEPTUN", "${days}")
                            Triple(celciusday, celciusnight, days)
                        }
                        .drop(1) // İlk elemanı hariç al
                        .let { result ->
                            val temperatureDayList = result.map { it.first.toString()+"°"}
                            val temperatureNightList = result.map { it.second.toString()+"°" }
                            val daysList = result.map { it.third }

                            oneWeekTemperatureDay.addAll(temperatureDayList)
                            oneWeekTemperatureNight.addAll(temperatureNightList)
                            oneWeekWeather.addAll(weatherData.daily.drop(1).map { it.weather.get(0).description })
                            dayOrder.addAll(daysList)
                        }
                    val recyclerView: RecyclerView = binding.recyclerView
                    val layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.layoutManager = layoutManager
                    val adapter = WeatherAdapter(dayOrder, oneWeekTemperatureDay, oneWeekTemperatureNight, oneWeekWeather)
                    adapter.setWeatherIcon(binding.currentIcon, currentIcon)
                    recyclerView.adapter = adapter

                    Log.d("JUPITER","TMPDAY: $oneWeekTemperatureDay")
                    Log.d("JUPITER","TMPNIGHT: $oneWeekTemperatureNight")
                    Log.d("JUPITER","WEATHER: $oneWeekWeather")


                }
            })

            return binding.root
        }




    }










