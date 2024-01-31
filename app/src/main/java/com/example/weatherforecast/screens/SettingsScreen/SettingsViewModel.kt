package com.example.weatherforecast.screens.SettingsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.model.Units
import com.example.weatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel() {
    private val _unitsList = MutableStateFlow<List<Units>>(emptyList())
    val unitsList = _unitsList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged().collect { listofUnits ->
                if (listofUnits.isNullOrEmpty())
                    Log.d("TAG", "Emptyfav")
                else {
                    _unitsList.value = listofUnits
                }

            }
        }
    }
    fun insertUnits(units: Units)=viewModelScope.launch { repository.insertUnits(units) }
    fun updateUnits(units: Units)=viewModelScope.launch { repository.updateUnits(units) }
    fun deleteUnits(units: Units)=viewModelScope.launch { repository.deleteUnits(units) }
    fun deleteAllUnits()=viewModelScope.launch { repository.deleteAllUnits() }
}
