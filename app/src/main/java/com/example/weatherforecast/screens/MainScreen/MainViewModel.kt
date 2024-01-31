package com.example.weatherforecast.screens.MainScreen

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.data.DataorEception
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
public class MainViewModel @Inject constructor(private val repository: WeatherRepository):ViewModel(){
    suspend fun getWeatherData(city: String, units: String):DataorEception<Weather,Boolean,Exception>{
        return repository.getWeather(cityQuery = city,units=units)
    }

}