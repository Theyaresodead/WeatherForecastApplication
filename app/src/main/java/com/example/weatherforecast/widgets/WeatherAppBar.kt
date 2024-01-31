package com.example.weatherforecast.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.navigation.WeatherScreen
import com.example.weatherforecast.screens.FavoriteScreen.FavoriteViewModel


@Composable
fun WeatherAppBar(
    title: String = "title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
){
     val showDialog =remember{
         mutableStateOf(false)
     }
    val showIt =remember{
        mutableStateOf(false)
    }
    val context= LocalContext.current
    if(showDialog.value){
        ShowDropDownMenu(showDialog=showDialog,navController=navController)
    }

    TopAppBar(title={
                    Text(text = title,color=MaterialTheme.colors.onSecondary,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                        textAlign = TextAlign.Center)
    }, actions = {
                 if(isMainScreen){
                     IconButton(onClick = {
                               onAddActionClicked.invoke()
                     }) {
                         Icon(imageVector = Icons.Default.Search, contentDescription = "search button")
                         
                     }
                     IconButton(onClick = { showDialog.value=true }) {
                         Icon(imageVector = Icons.Default.MoreVert, contentDescription ="More" )
                     }

                 }else Box{}
    }, navigationIcon = {
                        if(icon!=null) {
                            Icon(imageVector =icon, contentDescription =null,tint= MaterialTheme.colors.onSecondary,
                            modifier = Modifier.clickable {
                                onButtonClicked.invoke()
                            })
                        }
    if(isMainScreen){
        val isAlreadyFavList=favoriteViewModel.favList.collectAsState().value.filter { item->
            (item.city==title.split(",")[0])
        }
        if(isAlreadyFavList.isNullOrEmpty()){
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite Icon",
                modifier = Modifier
                    .scale(0.9f)
                    .clickable {
                        favoriteViewModel.insertFavorite(
                            Favorite(city = title.split(",")[0],
                                country = title.split(",")[1])).run { showIt.value=true }
                    },tint=Color.Red.copy(alpha = 0.6f) )
        }else {showIt.value=false
            Box{}}

         ShowToast(context = context,showIt)
    }
    },
        backgroundColor = Color.Transparent, elevation = elevation)

}

@Composable
fun ShowToast(context:Context, showIt:MutableState<Boolean>) {
   if(showIt.value){
       Toast.makeText(context,"Added To Favorites",Toast.LENGTH_SHORT).show()
   }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ShowDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var epanded by remember{
        mutableStateOf(true)
    }
    val items = listOf("About","Favorites","Settings")
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)) {
            DropdownMenu(expanded = epanded, onDismissRequest = {
                epanded=false }, modifier = Modifier
                .width(140.dp)
                .background(Color.White)) {
                items.forEachIndexed{ind,tet ->
                    DropdownMenuItem(onClick = {
                        epanded=false 
                        showDialog.value=false
                    }) {
                           Icon(imageVector = when(tet){
                                                       "About"->Icons.Default.Info
                                                        "Favorites" ->Icons.Default.Favorite
                                else -> Icons.Default.Settings
                                                       }
                               , contentDescription =null,tint=Color.LightGray)
                        Text(text = tet, modifier = Modifier.clickable {
                                                     navController.navigate(when(tet){
                                                         "About"-> WeatherScreen.AboutScreen.name
                                                         "Favorites" ->WeatherScreen.FavoriteScreen.name
                                                         else -> WeatherScreen.SettingsScreen.name
                                                     })
                        }, fontWeight = FontWeight.W300)
                    }

                }
            }
        }
}
