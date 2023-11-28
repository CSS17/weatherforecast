package com.example.weatherforecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherforecast.databinding.FragmentWeatherBinding

class WeatherFragment:Fragment() {
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentWeatherBinding.inflate(layoutInflater)

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


    class WeatherFragment : Fragment() {
        private lateinit var binding: FragmentWeatherBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Fragment'ın oluşturulması
            binding = FragmentWeatherBinding.inflate(inflater, container, false)

            // Getting Data
            val data = arguments?.getDoubleArray("KEY_DATA")
            Log.d("LOL", "${data?.get(0)}  ${data?.get(1)}")
            getCity(data)
            // Update the Textview on here
            binding.textview.text = "${getCity(data)}"



            return binding.root
        }


        private fun getCity(coordinates:DoubleArray?):String{
            val locationUtils = context?.let { LocationUtils(it) }
            var latitude = coordinates?.get(0)
            var longitude = coordinates?.get(1)

            val latitude2 = 39.7667
            val longitude2 = 30.5256
            val address2 = locationUtils?.getAddressFromLocation(latitude2, longitude2)
            println("Manuel Koordinat: $address2")


            val address = latitude?.let { longitude?.let { it1 ->
                locationUtils?.getAddressFromLocation(it,
                    it1
                )
            } }

            if (address != null) {
                println("Konum: $address")
                return address
            } else {
                println("Konum bilgisi alınamadı.")
                return "Location Couldn't Found."
            }

        }

    }



}