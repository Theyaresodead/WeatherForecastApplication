package com.example.weatherforecast.repository

import com.example.weatherforecast.data.DataorEception
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api:WeatherApi) {
     suspend fun getWeather(cityQuery: String, units: String):DataorEception<Weather,Boolean,Exception>{
         val response=try {
             api.getWeatherApi(query = cityQuery,units=units)
         }catch (e:Exception){
             return DataorEception(e=e)
         }
         return DataorEception(data=response)
     }
}