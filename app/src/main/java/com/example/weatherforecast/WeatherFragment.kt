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

            // Veriyi almak
            val data = arguments?.getDoubleArray("KEY_DATA")
            Log.d("LOL", "${data?.get(0)}  ${data?.get(1)}")

            // Textview'i güncelle
            binding.textview.text = "${data?.get(0)} /n ${data?.get(1)}"

            return binding.root
        }
    }



}