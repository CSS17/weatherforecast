package com.example.weatherforecast.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.service.RetrofitInstance
import kotlinx.coroutines.launch

class CityViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherModel>()
    val weatherData: LiveData<WeatherModel>
        get() = _weatherData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getWeatherData(lat: Double, lon: Double, exclude: String, appId: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.openWeatherMapApiService.getWeatherData(lat, lon, exclude, appId)
                if (response.isSuccessful) {
                    _weatherData.value = response.body()
                } else {
                    // API'den başarısız yanıt alındığında burada işlemleri gerçekleştirin
                    _errorMessage.value = "API request failed with code: ${response.code()}"
                    Log.d("ViewModel", "API request failed with code: ${response.code()}")
                }
            }
            catch (e: Exception) {
                // Hata durumunda burada işlemleri gerçekleştirin
                _errorMessage.value = "An error occurred: ${e.message}"
                Log.d("ViewModel", "An error occurred: ${e.message}")
            }
        }
    }
}