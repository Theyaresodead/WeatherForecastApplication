package com.example.weatherforecast.screens

import android.annotation.SuppressLint
import android.graphics.Paint.Align
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherforecast.R
import com.example.weatherforecast.navigation.WeatherScreen
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
@Composable
fun SplashScreen(navController: NavController){
     val animate = remember {
          Animatable(0f)
     }
 val defaultCity ="San Diego"
     LaunchedEffect(key1 = true, block ={
          animate.animateTo(targetValue = 0.9f, animationSpec = tween(
               durationMillis = 1000, easing = {
                    OvershootInterpolator(8f).getInterpolation(it) }))
          delay(1000)
          navController.navigate(WeatherScreen.MainScreen.name +"/$defaultCity")
     } )
     androidx.compose.material.Surface(modifier = Modifier
          .padding(14.dp)
          .size(250.dp)
          .scale(animate.value), shape = CircleShape,
          border = BorderStroke(2.dp, color = Color.Gray), color = Color.White) {
          Column(
               modifier = Modifier.padding(1.dp),
               horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
          ) {
               Image(painter = painterResource(id = R.drawable.sun),
                    contentDescription = "Sun Behind the Clouds", modifier = Modifier.size(95.dp),
               contentScale = ContentScale.Fit)
               Text(text = "Looking for Sun?", style = TextStyle(fontSize = 15.sp,
                    color = Color.Black, fontStyle = FontStyle.Italic))
          }

     }
}