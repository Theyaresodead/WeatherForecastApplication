package com.example.weatherforecast.repository

import com.example.weatherforecast.data.WeatherDao
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.model.Units
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorite(): Flow<List<Favorite>> =weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) =weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite)=weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorite()=weatherDao.deleteAllFavorite()
    suspend fun deleteFavorite(favorite: Favorite)=weatherDao.deleteFavorite(favorite)
    suspend fun getFavById(city:String):Favorite=weatherDao.getFavById(city)

    fun getUnits(): Flow<List<Units>> =weatherDao.getUnits()
    suspend fun insertUnits(units: Units) =weatherDao.insertUnits(units)
    suspend fun updateUnits(units: Units)=weatherDao.updateUnits(units)
    suspend fun deleteAllUnits()=weatherDao.deleteAllUnits()
    suspend fun deleteUnits(units: Units)=weatherDao.deleteUnits(units)
}