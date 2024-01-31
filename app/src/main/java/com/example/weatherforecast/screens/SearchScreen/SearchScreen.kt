package com.example.weatherforecast.screens.SearchScreen

import android.graphics.Paint.Align
import android.hardware.camera2.CameraCharacteristics.Key
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherforecast.navigation.WeatherScreen
import com.example.weatherforecast.widgets.WeatherAppBar


@Composable
fun SearchScreen(navController: NavController){
    Scaffold(topBar = { WeatherAppBar(navController = navController, icon = Icons.Default.ArrowBack,
        isMainScreen = false, title = "Search"){
        navController.popBackStack()
    }
    }) {
        Surface() {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                 SearchBar(modifier = Modifier.padding(6.dp).fillMaxWidth().align(Alignment.CenterHorizontally)){mcity->
                        navController.navigate(WeatherScreen.MainScreen.name+"/$mcity")
                 }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(modifier: Modifier=Modifier,onSearch:(String) -> Unit){
    val searchQueryState = rememberSaveable { mutableStateOf("") }
    val keyBoardController =LocalSoftwareKeyboardController.current
    val valid=remember(searchQueryState.value){
        searchQueryState.value.trim().isNotEmpty()
    }
   Column() {
       CFT(valueState=searchQueryState,placeholder="Seattle",onAction=KeyboardActions {
           if(!valid) return@KeyboardActions
           onSearch(searchQueryState.value.trim())
           searchQueryState.value=""
           keyBoardController?.hide()
       })
   }
}

@Composable
fun CFT(valueState:MutableState<String>,
        placeholder: String, keyboardtype:KeyboardType= KeyboardType.Text,imeAction:ImeAction=ImeAction.Next,
        onAction: KeyboardActions= KeyboardActions.Default) {
    OutlinedTextField(value = valueState.value, onValueChange ={valueState.value=it},
    label = { Text(text = placeholder)}, maxLines = 1, singleLine = true,
    keyboardOptions = KeyboardOptions(keyboardType = keyboardtype, imeAction =imeAction),
    keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.Blue,
            cursorColor = Color.Blue), shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp))

}
