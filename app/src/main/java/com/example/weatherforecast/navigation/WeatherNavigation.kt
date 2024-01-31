package com.example.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.screens.AboutScreen.AboutScreen
import com.example.weatherforecast.screens.FavoriteScreen.FavoriteScreen
import com.example.weatherforecast.screens.MainScreen.MainScreen
import com.example.weatherforecast.screens.MainScreen.MainViewModel
import com.example.weatherforecast.screens.SearchScreen.SearchScreen
import com.example.weatherforecast.screens.SettingsScreen.SettingsScreen
import com.example.weatherforecast.screens.SplashScreen

@Composable
fun WeatherNavigation(){
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreen.SplashScreen.name){
        composable(WeatherScreen.SplashScreen.name){
            SplashScreen(navController=navController)
        }
        val route=WeatherScreen.MainScreen.name
        composable("$route/{city}", arguments = listOf(navArgument(name = "city"){
            type= NavType.StringType
        })){
            navBack ->
            navBack.arguments?.getString("city").let{ city->
                val mainViewModel= hiltViewModel<MainViewModel>()
                MainScreen(navController = navController,mainViewModel,city=city)
            }
        }
        composable(WeatherScreen.SearchScreen.name){
            SearchScreen(navController = navController)
        }
        composable(WeatherScreen.AboutScreen.name){
            AboutScreen(navController = navController)
        }
        composable(WeatherScreen.FavoriteScreen.name){
            FavoriteScreen(navController = navController)
        }
        composable(WeatherScreen.SettingsScreen.name){
            SettingsScreen(navController = navController)
        }
    }
}