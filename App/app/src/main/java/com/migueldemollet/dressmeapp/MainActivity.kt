package com.migueldemollet.dressmeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.migueldemollet.dressmeapp.navigation.AppNavigation
import com.migueldemollet.dressmeapp.ui.theme.DressMeAppTheme
import dagger.hilt.android.AndroidEntryPoint

var screenWidth = 0.dp
var screenHeight = 0.dp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DressMeApp)
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
