package com.example.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.model.Units

@Database(entities = [Favorite::class,Units::class], version = 2, exportSchema = false)
abstract class WeatherDatabase:RoomDatabase() {
    abstract fun weatherDao():WeatherDao
}