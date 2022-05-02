package com.migueldemollet.dressmeapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migueldemollet.dressmeapp.model.Garment
import com.migueldemollet.dressmeapp.navigation.AppNavigation
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme

var screenWidth = 0.dp
var screenHeight = 0.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DressMeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    screenWidth = LocalConfiguration.current.screenWidthDp.dp
                    screenHeight = LocalConfiguration.current.screenHeightDp.dp
                    AppNavigation()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    DressMeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ){
            screenWidth = LocalConfiguration.current.screenWidthDp.dp
            screenHeight = LocalConfiguration.current.screenHeightDp.dp
            AppNavigation()
        }
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview2() {
    DressMeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ){
            //Voy a suponer que cuando clickemos en un producto y lo despleguemos, le pasemos la
            val garment = Garment(0, painterResource(id = R.drawable.ic_launcher_background),"Nombre del producto"
                , painterResource(id = R.drawable.ic_launcher_background)
            )
            //ClotheScreen(garment)
        }

    }
}

