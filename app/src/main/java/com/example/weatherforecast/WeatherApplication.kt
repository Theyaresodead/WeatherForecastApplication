package com.example.weatherforecast

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// The main class that binds all the components of the application in terms of dependency injection.
// That's why it inherits from the application
@HiltAndroidApp
class WeatherApplication:Application() {}