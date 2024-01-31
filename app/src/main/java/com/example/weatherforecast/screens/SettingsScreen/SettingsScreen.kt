package com.example.weatherforecast.screens.SettingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecast.model.Units
import com.example.weatherforecast.widgets.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController,settingsViewModel: SettingsViewModel= hiltViewModel()){
    var unitToogleButton by remember{
        mutableStateOf(false)
    }
    val measurementUnits=listOf("Imperial(F)","Metric(C)")
    val choicefromDb=settingsViewModel.unitsList.collectAsState().value
    val defaultChoice=if(choicefromDb.isNullOrEmpty()) measurementUnits[0] else choicefromDb[0].units

    var choiceState by remember{
        mutableStateOf(defaultChoice)
    }
    Scaffold(topBar = {
        WeatherAppBar(navController = navController,
            title = "Settings", isMainScreen = false, icon = Icons.Default.ArrowBack){
            navController.popBackStack()
        }}) {
               Surface(modifier = Modifier
                   .fillMaxWidth()
                   .fillMaxHeight()) {
                       Column(verticalArrangement = Arrangement.Center,
                           horizontalAlignment = Alignment.CenterHorizontally) {
                           Text(text = "Change Units Of Measurement",
                               modifier = Modifier.padding(bottom = 15.dp))
                           IconToggleButton(checked = !unitToogleButton, onCheckedChange = {
                                   unitToogleButton=!it
                               if(unitToogleButton) { choiceState="Imperial(F)"}
                               else{ choiceState="Metric(C)" }
                           }, modifier = Modifier
                               .fillMaxWidth(0.5f)
                               .clip(RectangleShape)
                               .padding(5.dp)
                               .background(Color.Magenta.copy(alpha = 0.5f))) {
                               Text(text = if(unitToogleButton) "Fahrenheit °F" else "Celsius °C")
                           }
                           Button(onClick = { settingsViewModel.deleteAllUnits()
                                            settingsViewModel.insertUnits(Units(units=choiceState))},
                               modifier = Modifier
                                   .padding(3.dp)
                                   .align(Alignment.CenterHorizontally), shape = RoundedCornerShape(34.dp),
                           colors = ButtonDefaults.buttonColors(
                               backgroundColor = Color(0xFFEFBE42)
                           )) {
                                        Text(text = "SAVE", modifier = Modifier.padding(4.dp),
                                            color = Color.White, fontSize = 14.sp)
                           } 
                       }
               }
    }

}
