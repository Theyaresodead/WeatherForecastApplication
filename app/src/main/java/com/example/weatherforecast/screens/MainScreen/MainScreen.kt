package com.example.weatherforecast.screens.MainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.weatherforecast.R
import com.example.weatherforecast.data.DataorEception
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.model.WeatherItem
import com.example.weatherforecast.navigation.WeatherScreen
import com.example.weatherforecast.screens.SettingsScreen.SettingsViewModel
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDateTime
import com.example.weatherforecast.utils.formatDecimals
import com.example.weatherforecast.widgets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),settingsViewModel: SettingsViewModel= hiltViewModel(),
    city: String?,
    ){
    val currCity:String=if(city!!.isBlank()) "Seattle" else city
     val unitFromDb =settingsViewModel.unitsList.collectAsState().value
    var units by remember{
        mutableStateOf("imperial")
    }
    var isImperial by remember{
        mutableStateOf(false)
    }
    if(!unitFromDb.isNullOrEmpty()){
        units=unitFromDb[0].units.split(",")[0].lowercase()
        isImperial= units=="imperial"
        val weatherData= produceState<DataorEception<Weather,Boolean,Exception>>(initialValue
        =DataorEception(loading =true) ){
            value=mainViewModel.getWeatherData(city = currCity,units=units)
        }.value
        if(weatherData.loading == true){
            CircularProgressIndicator()
        }else if(weatherData.data!=null) {
            MainScaffold(weather = weatherData.data, navController = navController,isImperial=isImperial)
        }
    }


}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
           Scaffold(topBar = { WeatherAppBar(
               title = weather.city.name+",${weather.city.country}",
               navController=navController, elevation = 5.dp, icon = Icons.Default.ArrowBack,
               onAddActionClicked = {
                    navController.navigate(WeatherScreen.SearchScreen.name)
               }
           )}, drawerElevation = 1.dp) {
               MainContent(data=weather,isImperial=isImperial)
           }

}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    val weatherItem = data.list[0]
    val imageUrl ="https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
      Column(modifier = Modifier
          .padding(4.dp)
          .fillMaxWidth(),
          verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = formatDate(weatherItem.dt),
                  style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSecondary,
              modifier = Modifier.padding(6.dp))
          Surface(modifier = Modifier
              .padding(4.dp)
              .size(200.dp), shape = CircleShape, color = Color(0xFFFFC400)) {
              Column(verticalArrangement = Arrangement.Center,
                  horizontalAlignment = Alignment.CenterHorizontally) {
                  WeatherStateImage(imageUrl = imageUrl)
                  Text(text = formatDecimals(weatherItem.temp.day)+"°", style = MaterialTheme.typography.h4,
                      fontWeight = FontWeight.ExtraBold)
                  Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)

              } }
          HumidityWindPressure(weather = weatherItem,isImperial=isImperial)
          Divider(Modifier.height(2.dp))
          SunriseAndSet(weather=weatherItem)
          CardView(weather = data)
      
      }

}

@Composable
fun CardView(weather: Weather) {
      Text(text = "This Week",
          style = MaterialTheme.typography.caption,
          color = MaterialTheme.colors.onSecondary, fontWeight = FontWeight.Bold)
      Surface(modifier = Modifier
          .padding(5.dp)
          .fillMaxWidth()
          .fillMaxHeight()
          , color = Color(0xFFEEEF1EF), shape = RoundedCornerShape(size = 14.dp)) {
          LazyColumn(modifier = Modifier.padding(2.dp),
              contentPadding = PaddingValues(1.dp)){
              items(items=weather.list) { item: WeatherItem ->  
                  WeatherDetailRow(weather = item)

          }

          }

      }

}

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl ="https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)), color = Color.White) {
         Row(modifier = Modifier.fillMaxWidth(),
             verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
              Text(text = formatDate(weather.dt).split(",")[0],
                  modifier = Modifier.padding(start = 5.dp))
             WeatherStateImage(imageUrl = imageUrl)
             Surface(modifier = Modifier.padding(0.dp),
                 shape = CircleShape,
                 color = Color(0xFFFFC400)) {
                 Text(text = weather.weather[0].description,
                     modifier = Modifier.padding(4.dp),style=MaterialTheme.typography.caption)
             }
             Text(text = buildAnnotatedString {
                 withStyle(style = SpanStyle(
                     color=Color.Blue.copy(alpha = 0.7f),
                     fontWeight = FontWeight.SemiBold)){
                     append(formatDecimals(weather.temp.max)+"°")
                 }
                 withStyle(style = SpanStyle(
                     color=Color.LightGray)){
                     append(formatDecimals(weather.temp.min)+"°")}
             })
         }
    }

}

@Composable
fun SunriseAndSet(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunrise),
                contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "${formatDateTime(weather.sunrise)}", style = MaterialTheme.typography.caption)

        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunset),
                contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "${formatDateTime(weather.sunset)}", style = MaterialTheme.typography.caption)

        }


    }

}

@Composable
fun HumidityWindPressure(weather: WeatherItem, isImperial: Boolean) {
        Row(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.padding(4.dp)) {
                Icon(painter = painterResource(id = R.drawable.humidity), 
                    contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "${weather.humidity}%", style = MaterialTheme.typography.caption)
                
            }
            Row(modifier = Modifier.padding(4.dp)) {
                Icon(painter = painterResource(id = R.drawable.wind),
                    contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "${weather.speed}"+if(isImperial) "mph" else "m/s"
                    , style = MaterialTheme.typography.caption)

            }
            Row(modifier = Modifier.padding(4.dp)) {
                Icon(painter = painterResource(id = R.drawable.pressure),
                    contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(3.dp))
                Text(text = "${weather.pressure}psi", style = MaterialTheme.typography.caption)

            }

        }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberImagePainter(imageUrl), contentDescription ="icon image",
        modifier = Modifier.size(80.dp) )

}
