package com.example.weatherforecast.data

import androidx.room.*
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.model.Units
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * from fav_tbl")
    fun getFavorites():Flow<List<Favorite>>

    @Query("SELECT * from fav_tbl where city=:city")
   suspend fun getFavById(city:String):Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavorite()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    // Units
    @Query("SELECT * from settings_table")
    fun getUnits():Flow<List<Units>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnits(units: Units)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnits(units: Units)

    @Query("DELETE from settings_table")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnits(units: Units)

}
