package com.example.weatherforecast.viewmodel

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R

class WeatherAdapter(private val daysOrder: MutableList<String>, private val temperaturesDay: List<String>, private val temperaturesNight: List<String>, private val weathers: List<String>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder içinde kullanılacak bileşenleri tanımlayın (örneğin, TextView, ImageView vb.)
        val dayOrder: TextView = itemView.findViewById(R.id.dayname)
        val temperatureDay: TextView = itemView.findViewById(R.id.tmpday)
        val temperatureNight: TextView = itemView.findViewById(R.id.tmpnight)
        val weatherImageView: ImageView = itemView.findViewById(R.id.weathericon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // ViewHolder'ın içeriğini doldurun
        holder.dayOrder.text = daysOrder[position].toString()
        holder.temperatureDay.text = temperaturesDay[position].toString()
        holder.temperatureNight.text = temperaturesNight[position].toString()
        // setWeatherIcon fonksiyonunu kullanarak ImageView'e resmi ayarlayın
        setWeatherIcon(holder.weatherImageView, weathers[position])
    }

    override fun getItemCount(): Int {
        return temperaturesDay.size
    }

    fun setWeatherIcon(imageView: ImageView, weather: String) {
        when (weather) {
            "clear sky" -> imageView.setImageResource(R.drawable.clear_sky)
            "few clouds" -> imageView.setImageResource(R.drawable.few_clouds)
            "scattered clouds" -> imageView.setImageResource(R.drawable.scattered_clouds)
            "broken clouds", "overcast clouds" -> imageView.setImageResource(R.drawable.broken_clouds)
            "mist", "smoke", "haze", "sand/dust whirls", "fog", "sand", "dust", "volcanic ash", "squalls", "tornado" -> imageView.setImageResource(R.drawable.mist)
            "light snow", "snow", "heavy snow", "sleet", "light shower sleet", "shower sleet", "light rain and snow",
            "rain and snow", "light shower snow", "shower snow", "heavy shower snow", "freezing rain" -> imageView.setImageResource(R.drawable.snow)
            "light rain", "moderate rain", "heavy intensity rain", "very heavy rain", "extreme rain" -> imageView.setImageResource(R.drawable.rain)
            "light intensity shower rain", "shower rain", "heavy intensity shower rain", "ragged shower rain",
            "shower drizzle", "heavy shower rain and drizzle", "shower rain and drizzle", "heavy intensity drizzle rain",
            "drizzle rain", "light intensity drizzle rain", "heavy intensity drizzle", "drizzle", "light intensity drizzle" -> imageView.setImageResource(R.drawable.shower_rain)
            "thunderstorm with light rain", "thunderstorm with rain", "thunderstorm with heavy rain", "light thunderstorm", "thunderstorm",
            "heavy thunderstorm", "ragged thunderstorm", "thunderstorm with light drizzle", "thunderstorm with drizzle", "thunderstorm with heavy drizzle" -> imageView.setImageResource(R.drawable.thunderstorm)
            else -> Log.e("NO RESOURCE","IMAGE COULDN'T FIND")
        }
    }

}
