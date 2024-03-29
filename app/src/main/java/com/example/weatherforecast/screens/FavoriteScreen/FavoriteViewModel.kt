package com.example.weatherforecast.screens.FavoriteScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.repository.WeatherDbRepository
import com.example.weatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository):ViewModel(){
    private val _favList= MutableStateFlow<List<Favorite>>(emptyList())
    val favList=_favList.asStateFlow()

    init {
        viewModelScope.launch ( Dispatchers.IO ){
            repository.getFavorite().distinctUntilChanged().collect{
                listofFav->
                if(listofFav.isNullOrEmpty())
                    Log.d("TAG","Emptyfav")
                            else{
                                _favList.value=listofFav
                }

            }
        }
    }
    fun insertFavorite(favorite: Favorite)=viewModelScope.launch { repository.insertFavorite(favorite) }
    fun updateFavorite(favorite: Favorite)=viewModelScope.launch { repository.updateFavorite(favorite) }
    fun deleteFavorite(favorite: Favorite)=viewModelScope.launch { repository.deleteFavorite(favorite) }


}